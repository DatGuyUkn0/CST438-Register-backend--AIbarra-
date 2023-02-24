package com.cst438.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cst438.domain.StudentDTO;

@RestController
public class StudentController {
	
	@PostMapping("/student")
	@Transactional
	public StudentDTO CreateNewStudent()
	{
		
	}
	
	
	@GetMapping("/student")
	
	@DeleteMapping("/student/{student_id}")
	@Transactional
	public void DeleteStudent(@ParameterRequest)

}
