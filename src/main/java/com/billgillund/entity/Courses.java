package com.billgillund.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="COURSES")
public class Courses {
	@Id
	@Column(name="COURSE_ID")
	@GeneratedValue(strategy= GenerationType.AUTO)
	private long courseId;
	@Column(name="COURSE_NAME")
	private String courseName;
	@Column(name="SLOPE")
	private int slope;
	@Column(name="RATING")
	private float rating;
	@Column(name="YARDAGE")
	private int yardage;
	@Column(name="COMMENT")
	private String comment;
	public long getCourseId() {
		return courseId;
	}
	public void setCourseId(long courseId) {
		this.courseId = courseId;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public int getSlope() {
		return slope;
	}
	public void setSlope(int slope) {
		this.slope = slope;
	}
	public float getRating() {
		return rating;
	}
	public void setRating(float rating) {
		this.rating = rating;
	}
	public int getYardage() {
		return yardage;
	}
	public void setYardage(int yardage) {
		this.yardage = yardage;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
}
	
	
	
