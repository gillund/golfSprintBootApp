package com.billgillund.business.services;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.billgillund.entity.Courses;
import com.billgillund.repository.CoursesRepository;

@Service
public class CourseServiceImpl implements CourseService
{
    public static Hashtable<Long,Courses> tempCourses = new Hashtable();
    private String filename;
    private Hashtable courses ;
    @Autowired
    CoursesRepository courseRepository;
    
    public CourseServiceImpl() {
       
    }
   public Hashtable getAllCourses() {
        return (Hashtable) getCourses().clone();
    }
    public Courses getCourse(long courseId) {
        return (Courses) getCourses().get(courseId);
    }

    public Hashtable addCourse(String aName, float aRating, int aSlope, int theYardage, String theComments) throws Exception {
        if ( aName == null || aName.equals("") ) {
            throw new Exception("Illegal Course Name: Course Name Missing");
        }
        if ( courses.containsKey(aName) ) {
            throw new Exception("Illegal Course Name: Course Already Exists");
        }
        if ( theComments.equals("") ) {
            theComments = " ";
        }
        
       
        Courses newCourses = new Courses();
        newCourses.setCourseName(aName);
        newCourses.setRating(aRating);
        newCourses.setSlope(aSlope);
        newCourses.setYardage(theYardage);
        newCourses.setComment(theComments);
    	
        Courses c = courseRepository.save(newCourses);
        
        courses.put(c.getCourseId(),c);
       
        
      
        return getCourses();
    }
    public Hashtable removeCourse(long courseId) throws Exception {
    	
    	
    	Courses course = (Courses) courses.get(courseId);
    	if (course == null)
    	{
    		System.err.println("Invalid Course should be in hashmap ");
    		throw new Exception ("Can't delete course does not exist");
    	}
        courseRepository.delete(course);
        courses.remove(course.getCourseId());
        return getCourses();
    
    }
    protected String getFilename() {
         return filename;
    }
    
    protected Hashtable getCourses() {
    
       if ( courses == null ) {
            courses = readCourses(getFilename());
         }
        return courses;
     
    	
    }

    protected Hashtable readCourses(String aFilename) {
    	 Iterable <Courses> al = courseRepository.findAll();

	        al.forEach(course ->{
	        	Courses c = new Courses();
	        	c.setCourseId(course.getCourseId());
	        	c.setComment(course.getComment());
	        	c.setCourseName(course.getCourseName());
	        	c.setRating(course.getRating());
	        	c.setSlope(course.getSlope());
	        	c.setYardage(course.getYardage());
	        	tempCourses.put(course.getCourseId(), c);
	        }) ;
	        
    	
        return tempCourses;
    }

    protected BufferedReader getInputStream(String aFilename) throws IOException {
        return new BufferedReader(new FileReader(aFilename));
    }
	
  
   
    
}