package com.wit.s17challenge.controller;

import com.wit.s17challenge.dto.CourseResponse;
import com.wit.s17challenge.dto.CourseResponseFactory;
import com.wit.s17challenge.exeptions.CourseException;
import com.wit.s17challenge.exeptions.CourseValidation;
import com.wit.s17challenge.model.Course;
import com.wit.s17challenge.model.CourseGpa;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin("*") //CORS exceptiona karşı
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
           throw new CourseException("Course with given name is not exist: "+name, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/")
    public CourseResponse save(@RequestBody Course course) {
        CourseValidation.isIdValid(course.getId());
        CourseValidation.isDuplicateNameFound(courses,course.getName());
        CourseValidation.checkCourseIsValid(course);
    courses.add(course);
        return CourseResponseFactory.createCourseResponse(course, low, medium, high);
    }

    @PutMapping("/{id}")
    public Course update(@RequestBody Course course, @PathVariable int id) {
        CourseValidation.checkCourseIsValid(course);
        Optional<Course> optionalCourse = courses.stream().filter(c -> c.getId() == id).findFirst();
        if (optionalCourse.isPresent()) {
            int index = courses.indexOf(optionalCourse.get());
            course.setId(id);
            courses.set(index, course);
            return course;
        } else {
            throw new CourseException("Course with given id is not exist: "+id, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public Course delete(@PathVariable int id){
        Optional<Course> optionalCourse = courses.stream().filter(c -> c.getId() == id).findFirst();
        if (optionalCourse.isPresent()) {
            int index=courses.indexOf(optionalCourse.get());
            courses.remove(index);
            return optionalCourse.get();
        }else{
            throw new CourseException("Course with given id is not exist: "+id, HttpStatus.NOT_FOUND);
        }
    }
}
