package com.cbr.university.controller.rest;

import com.cbr.university.dto.StudentDtoRest;
import com.cbr.university.model.Student;
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
@RequestMapping("rest/students")
@Validated
public class StudentRestController {
    private static final String CUSTOM_HEADER_NAME = "X-Query-Result";
    private final HttpHeaders headers = new HttpHeaders();
    private final ModelMapper modelMapper;
    private final BaseService<Student> studentService;

    @Autowired
    public StudentRestController(BaseService<Student> studentService, ModelMapper modelMapper) {
        this.studentService = studentService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAll() {
        List<Student> students = studentService.getAll();
        headers.clear();
        headers.add(CUSTOM_HEADER_NAME,
                "All objects Student found. Number of objects " + students.size());
        return new ResponseEntity<>(students, headers, HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Student> add(
            @Validated(Create.class) @RequestBody StudentDtoRest studentDtoRest) {
        Student student = modelMapper.map(studentDtoRest, Student.class);
        Student createdStudent = studentService.create(student);
        headers.clear();
        headers.add(CUSTOM_HEADER_NAME, "Created Student object with id " + createdStudent.getId());
        return new ResponseEntity<>(createdStudent, headers, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Student> update(
            @Validated(Update.class) @RequestBody StudentDtoRest studentDtoRest) {
        Student student = modelMapper.map(studentDtoRest, Student.class);
        Student updatedStudent = studentService.update(student);
        headers.clear();
        headers.add(CUSTOM_HEADER_NAME, "Updated Student object with id " + updatedStudent.getId());
        return new ResponseEntity<>(updatedStudent, headers, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Student> delete(
            @NotNull(message = "Request must include a Student id")
            @IdExistsInDb(typeObject = "Student", message = "This Student id is not in the database")
            @PathVariable Integer id) {
        studentService.delete(studentService.getById(id));
        headers.clear();
        headers.add(CUSTOM_HEADER_NAME, "Deleted Student object with id " + id);
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }
}
