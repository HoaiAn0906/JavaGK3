package entity;

import java.util.List;

import org.bson.codecs.pojo.annotations.BsonId;

public class Course {
	@BsonId
	private String id;
	private int credits;
	private String title;
	private List<Section> sections;

	public Course() {
		super();
	}

	public Course(String id, int credits, String title, List<Section> sections) {
		super();
		this.id = id;
		this.credits = credits;
		this.title = title;
		this.sections = sections;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getCredits() {
		return credits;
	}

	public void setCredits(int credits) {
		this.credits = credits;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Section> getSections() {
		return sections;
	}

	public void setSections(List<Section> sections) {
		this.sections = sections;
	}

	@Override
	public String toString() {
		return "Course [id=" + id + ", credits=" + credits + ", title=" + title + ", sections=" + sections + "]";
	}
}
