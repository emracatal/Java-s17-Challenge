package com.wit.s17challenge.controller;

import com.wit.s17challenge.dto.CourseResponse;
import com.wit.s17challenge.dto.CourseResponseFactory;
import com.wit.s17challenge.model.Course;
import com.wit.s17challenge.model.CourseGpa;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController//controlleri spring containere tanıt ve build sırasında courseController instance oluşturur
@RequestMapping("/courses")
public class CourseController {
    private List<Course> courses;
    private CourseGpa low;
    private CourseGpa medium;

    @Autowired
    public CourseController(@Qualifier("lowCourseGpa") CourseGpa low, @Qualifier("mediumCourseGpa") CourseGpa medium, @Qualifier("highCourseGpa") CourseGpa high) {
        this.low = low;
        this.medium = medium;
        this.high = high;
    }

    private CourseGpa high;

    @PostConstruct
    public void init() {
        courses = new ArrayList<>();
    }

    @GetMapping("/")
    public List<Course> getAll() {
        return courses;
    }

    @GetMapping("/{name}")
    public Course find(@PathVariable String name) {
        Optional<Course> optionalCourse = courses.stream().filter(course -> course.getName().equals(name)).findFirst();
        if (optionalCourse.isPresent()) {
            return optionalCourse.get();
        } else {
            //TODO THROW COURSE NOT FOUND EXCEPTION
            return null;
        }
    }

    @PostMapping("/")
    public CourseResponse save(@RequestBody Course course) {
        //TODO VALIDATION
        courses.add(course);
        return CourseResponseFactory.createCourseResponse(course, low, medium, high);
    }

    @PutMapping("/{id}")
    public Course update(@RequestBody Course course, @PathVariable int id) {
        //TODO VALIDATION
        Optional<Course> optionalCourse = courses.stream().filter(c -> c.getId() == id).findFirst();
        if (optionalCourse.isPresent()) {
            int index = courses.indexOf(optionalCourse.get());
            course.setId(id);
            courses.set(index, course);
            return course;
        } else {
            //TODO THROW EXCEPTION
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public Course delete(@PathVariable int id){
        Optional<Course> optionalCourse = courses.stream().filter(c -> c.getId() == id).findFirst();
        if (optionalCourse.isPresent()) {
            int index=courses.indexOf(optionalCourse.get());
            courses.remove(index);
            return optionalCourse.get();
        }else{
            //THROW EXCEPTION
        }
        return null;
    }
}
