package com.billgillund.repository;

import org.springframework.data.repository.CrudRepository;

import com.billgillund.entity.Courses;
import com.billgillund.entity.Round;

public interface CoursesRepository extends CrudRepository<Courses,Long>{
	Round findBycourseId(final String courseId);
}
