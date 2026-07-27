package com.wipro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.entity.Student;
import com.wipro.service.StudentService;

@RestController
@RequestMapping("/students")
public class StudentController {
	 @Autowired
	 private final StudentService service;
	 
	 @Autowired
	 private PasswordEncoder passwordEncoder;

	    public StudentController(StudentService service) {
	        this.service = service;
	    }

	    @PostMapping
	    public Student saveStudent(@RequestBody Student student) {
	    	System.out.println("Before Save: " + student);
	    	student.setPassword(passwordEncoder.encode(student.getPassword()));
	        Student savedStudent = service.saveStudent(student);
	        System.out.println("After Save: " + savedStudent);
	        return savedStudent;
	    }

	    @GetMapping("/all")
	    public List<Student> getAllStudents() {
	        return service.getAllStudents();
	    }

	    @GetMapping("/{id}")
	    public Student getStudent(@PathVariable Long id) {
	        return service.getStudentById(id);
	    }

	    @PutMapping("/{id}")
	    public Student updateStudent(@PathVariable Long id,
	                                 @RequestBody Student student) {

	        return service.updateStudent(id, student);
	    }
	    @DeleteMapping("/{id}")
	    public String deleteStudent(@PathVariable Long id) {

	        service.deleteStudent(id);

	        return "Student deleted successfully";
	    }
}
