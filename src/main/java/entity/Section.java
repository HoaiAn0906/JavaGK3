package entity;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;

public class Section {
	@BsonId
	private String id;
	private int year;
	private String semester;
	@BsonProperty("professor_name")
	private String professorName;

	public Section(String id, int year, String semester, String professorName) {
		super();
		this.id = id;
		this.year = year;
		this.semester = semester;
		this.professorName = professorName;
	}

	public Section() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public String getProfessorName() {
		return professorName;
	}

	public void setProfessorName(String professorName) {
		this.professorName = professorName;
	}

	@Override
	public String toString() {
		return "Section [id=" + id + ", year=" + year + ", semester=" + semester + ", professorName=" + professorName
				+ "]";
	}
}
