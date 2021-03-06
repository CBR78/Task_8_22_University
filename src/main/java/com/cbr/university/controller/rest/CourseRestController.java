package com.cbr.university.controller.rest;

import com.cbr.university.dto.CourseDtoRest;
import com.cbr.university.model.Course;
import com.cbr.university.service.BaseService;
import com.cbr.university.validation.IdExistsInDb;
import com.cbr.university.validation.group.Create;
import com.cbr.university.validation.group.Update;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("rest/courses")
@Validated
public class CourseRestController {
    private static final String CUSTOM_HEADER_NAME = "X-Query-Result";
    private final HttpHeaders headers = new HttpHeaders();
    private final ModelMapper modelMapper;
    private final BaseService<Course> courseService;

    @Autowired
    public CourseRestController(BaseService<Course> courseService, ModelMapper modelMapper) {
        this.courseService = courseService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<Course>> getAll() {
        List<Course> courses = courseService.getAll();
        headers.clear();
        headers.add(CUSTOM_HEADER_NAME, "All objects Course found. Number of objects " + courses.size());
        return new ResponseEntity<>(courses, headers, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Course> add(
            @Validated(Create.class) @RequestBody CourseDtoRest courseDtoRest) {
        Course course = modelMapper.map(courseDtoRest, Course.class);
        Course createdCourse = courseService.create(course);
        headers.clear();
        headers.add(CUSTOM_HEADER_NAME, "Created Course object with id " + createdCourse.getId());
        return new ResponseEntity<>(createdCourse, headers, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Course> update(
            @Validated(Update.class) @RequestBody CourseDtoRest courseDtoRest) {
        Course course = modelMapper.map(courseDtoRest, Course.class);
        Course updatedCourse = courseService.update(course);
        headers.clear();
        headers.add(CUSTOM_HEADER_NAME, "Updated Course object with id " + updatedCourse.getId());
        return new ResponseEntity<>(updatedCourse, headers, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Course> delete(
            @NotNull(message = "Request must include a Course id")
            @IdExistsInDb(typeObject = "Course", message = "This Course id is not in the database")
            @PathVariable Integer id) {
        courseService.delete(courseService.getById(id));
        headers.clear();
        headers.add(CUSTOM_HEADER_NAME, "Deleted Course object with id " + id);
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }
}
