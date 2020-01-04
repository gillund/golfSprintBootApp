package com.billgillund.business.services;

import java.util.Hashtable;

import com.billgillund.entity.Courses;

public interface CourseService {
	
	    public Hashtable getAllCourses();
	    
	    public Courses getCourse(long courseId);

	    public Hashtable addCourse(String aName, float aRating, int aSlope, int theYardage, String theComments) throws Exception;

	    public Hashtable removeCourse(long courseId) throws Exception;


}
