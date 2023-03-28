package entity;

public class Enrollment {
	private Section section;
	private String grade;

	public Enrollment(Section section, String grade) {
		super();
		this.section = section;
		this.grade = grade;
	}

	public Enrollment() {
		super();
	}

	public Section getSection() {
		return section;
	}

	public void setSection(Section section) {
		this.section = section;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	@Override
	public String toString() {
		return "Enrollment [section=" + section + ", grade=" + grade + "]";
	}
}
