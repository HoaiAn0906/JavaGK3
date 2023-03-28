package entity;

import java.util.Date;
import java.util.List;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;

import com.google.gson.annotations.SerializedName;

public class Student {
	@SerializedName("_id")
	@BsonId
	private String id;
	private String gender;
	@BsonProperty("date_of_birth")
	@SerializedName("date_of_birth")
	private Date dateOfBirth;
	private String name;
	private List<Enrollment> enrollments;

	public Student(String id, String gender, Date dateOfBirth, String name, List<Enrollment> enrollments) {
		super();
		this.id = id;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.name = name;
		this.enrollments = enrollments;
	}

	public Student() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Enrollment> getEnrollments() {
		return enrollments;
	}

	public void setEnrollments(List<Enrollment> enrollments) {
		this.enrollments = enrollments;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", gender=" + gender + ", dateOfBirth=" + dateOfBirth + ", name=" + name
				+ ", enrollments=" + enrollments + "]";
	}
}
