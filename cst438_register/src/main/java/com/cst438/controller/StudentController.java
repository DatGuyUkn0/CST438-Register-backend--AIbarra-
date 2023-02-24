package com.cst438.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.cst438.domain.Student;
import com.cst438.domain.StudentDTO;
import com.cst438.domain.StudentRepository;

@RestController
public class StudentController {
	
	@Autowired
	StudentRepository studentRepository;
	
	@PostMapping("/student")
	@Transactional
	public StudentDTO CreateNewStudent(@RequestParam("email")String email, @RequestParam("name") String name) {
		String present = email;
		Student student = studentRepository.findByEmail(present);
		if(student == null) {
			System.out.println("New Student has been added!");
			StudentDTO NewStudent() // What is the new Student object? How is it created? should I initialize ID will it register that?
			return NewStudent;
		}else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is already in use.");
		}
	}
	
	
	@GetMapping("/student")
	
	@DeleteMapping("/student/{student_id}")
	@Transactional
	public void DeleteStudent(@RequestParam("student_id") String student_id){
		
	}

}
