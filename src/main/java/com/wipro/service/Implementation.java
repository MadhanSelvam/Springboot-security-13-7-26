package com.wipro.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.entity.Student;
import com.wipro.repo.StudentRepo;

@Service
public class Implementation implements StudentService {

	@Autowired
	private StudentRepo studentRepo;
	
		//postmapping
	 	public Student saveStudent(Student student) {
	        return studentRepo.save(student);
	    }
	 	//getmapping all
	    public List<Student> getAllStudents() {
	        return studentRepo.findAll();
	    }
	    //getmapping by id
	    public Student getStudentById(Long id) {
	        return studentRepo.findById(id)
	                .orElseThrow(() -> new RuntimeException("Student not found"));
	    }
	    //putmapping by id
	    public Student updateStudent(Long id, Student student) {

	        Student existing = studentRepo.findById(id)
	                .orElseThrow(() -> new RuntimeException("Student not found"));

	        existing.setDept(student.getDept());
	        existing.setLocation(student.getLocation());
	        existing.setEmail(student.getEmail());

	        return studentRepo.save(existing);
	    }
	    //delete by id
	    public void deleteStudent(Long id) {

	        Student student = studentRepo.findById(id)
	                .orElseThrow(() -> new RuntimeException("Student not found"));

	        studentRepo.delete(student);
	    }

}
