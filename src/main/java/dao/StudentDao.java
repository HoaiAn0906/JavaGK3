package dao;

import org.bson.codecs.configuration.CodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.reactivestreams.Publisher;

import com.google.gson.Gson;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;

import static com.mongodb.client.model.Filters.*;

import com.mongodb.reactivestreams.client.AggregatePublisher;
import com.mongodb.reactivestreams.client.FindPublisher;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoCollection;
import com.mongodb.reactivestreams.client.MongoDatabase;

import db.Connection;
import entity.Enrollment;
import entity.Section;
import entity.Student;

public class StudentDao {
	private MongoCollection<Document> collection;
	private MongoClient mongoClient;
	private MongoDatabase mongoDb;
	
	public StudentDao() {
		mongoClient = Connection.getInstance().getMongoClient();
		mongoDb = mongoClient.getDatabase("coursedb");
	
		collection = mongoDb.getCollection("students", Document.class);
	}
	
	//a. (2.0 điểm) Thêm một sinh viên mới
	public boolean addStudent(Student student) {
		Gson gson = new Gson();
		String json = gson.toJson(student);
		Document docStudent = Document.parse(json);
		
		System.out.println(docStudent);
		
		ArrayList<Document> doc = (ArrayList<Document>) docStudent.get("enrollments"); 
		
		for (Document document : doc) {
			String section_id = document.get("section", Document.class).getString("id");
			document.remove("section");
			document.append("section_id", section_id);
		}
		
		System.out.println(docStudent);
		 
		Publisher<InsertOneResult> publisher = collection.insertOne(docStudent);
		CourseSubcriber<InsertOneResult> subcriber = new CourseSubcriber<InsertOneResult>();
		publisher.subscribe(subcriber);
		
		return subcriber.getSingleResult().getInsertedId() != null;
	}
	
	//(2.0 điểm) Bổ sung một học phần mới mà sinh viên tham gia khi biết mã số sinh viên
	public boolean updateEnrollments(String studentId, Enrollment newEnrollment) {
		Gson gson = new Gson();
		String json = gson.toJson(newEnrollment);
		Document doc = Document.parse(json);
		
		doc.remove("section");
		doc.append("seaction_id", newEnrollment.getSection().getId());
		
		Publisher<UpdateResult> publisher = collection.updateOne(Filters.eq("_id", studentId), 
				Document.parse("{$push: { enrollments : "+doc.toJson()+"}}"));
		CourseSubcriber<UpdateResult> subcriber = new CourseSubcriber<UpdateResult>();
		publisher.subscribe(subcriber);
		
		return subcriber.getSingleResult().getModifiedCount() > 0;
	}
	
	//Thống kê số lượng học phần của sinh viên đã tham gia khi biết mã số sinh viên 
	//getNumberSectionsOfStudent(studentId: String) : int
	//db.students.aggregate([{$match: {"_id" : "1234567"}}, {$project : {numberSection : {$size : "$enrollments"}}}])
	public int getNumberSectionsOfStudent(String studentId) {
		AggregatePublisher<Document> publisher = collection.aggregate(Arrays.asList(
				Document.parse("{$match: {\"_id\" : \"1234567\"}}"),
				Document.parse("{$project : {numberSection : {$size : \"$enrollments\"}}}")
				));
		CourseSubcriber<Document> subcriber = new CourseSubcriber<Document>();
		publisher.subscribe(subcriber);
		
		return subcriber.getSingleResult().getInteger("numberSection");
	}
	
	//(2.0 điểm) Tìm danh sách các sinh viên đạt điểm nào đó, khi tham gia học phần của khóa
	//học khi biết mã khóa học
	//getStudentsByGrade(courseId: String, grade: String) : List<Student>
	//db.students.aggregate([{$match:{enrollments:{$exists:true}}},
	//{$unwind:'$enrollments'},
	//{$lookup:{from:'courses', localField:'enrollments.section_id', foreignField:'sections', as:'rs'}},
	//{$match:{$and:[{'rs._id':'CSC347'},{'enrollments.grade':'B'}]}},
	//{$unset:'rs'}])
	public List<Student> getStudentsByGrade(String courseId, String grade) {
		Gson gson = new Gson();
		AggregatePublisher<Document> publisher = collection.aggregate(Arrays.asList(
				Document.parse("{$match:{enrollments:{$exists:true}}}"),
				Document.parse("{$unwind:'$enrollments'}"),
				Document.parse("{$lookup:{from:'courses', localField:'enrollments.section_id', foreignField:'sections', as:'rs'}}"),
				Document.parse("{$match:{$and:[{'rs._id':'CSC347'},{'enrollments.grade':'B'}]}}"),
				Document.parse("{$unset:'rs'}")
				));
		CourseSubcriber<Document> subcriber = new CourseSubcriber<Document>();
		publisher.subscribe(subcriber);
		
		List<Student> students = new ArrayList<Student>();
		
		for (Document doc : subcriber.getResults()) {
			Student student = new Student();
			student.setId(doc.getString("_id"));
			student.setGender(doc.getString("gender"));
			student.setDateOfBirth(doc.getDate("date_of_birth"));
			student.setName(doc.getString("name"));
			Document docEnrolls = doc.get("enrollments", Document.class);
			List<Enrollment> enrollments = new ArrayList<Enrollment>();
			Enrollment enrollment = new Enrollment(new Section(docEnrolls.getString("section_id"), 0, null, null), docEnrolls.getString("grade"));
			enrollments.add(enrollment);
			student.setEnrollments(enrollments);
			students.add(student);
		}
		
		return students;
	}
}
