package app;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import dao.StudentDao;
import entity.Enrollment;
import entity.Section;
import entity.Student;

public class app {
	public static void main(String[] args) {
		List<Enrollment> enrollments = new ArrayList<Enrollment>();
		enrollments.add(new Enrollment(new Section("123", 2023, "test", "an test"), "A-"));
//		Date now = new Date();
//		Student student = new Student("1234567", "Nam", now, "An", enrollments);
		StudentDao studentDao = new StudentDao();
//		boolean temp = studentDao.addStudent(student);
//		System.out.println(temp);
		
//		Enrollment enrollment = new Enrollment(new Section("123", 1233, "ádasd", "adsdasd"), "đâsd");
//		
//		System.out.println(studentDao.updateEnrollments("1234567", enrollment));
		studentDao.getStudentsByGrade("CSC347", "B").forEach(s -> System.out.println(s));
		
	}
}
