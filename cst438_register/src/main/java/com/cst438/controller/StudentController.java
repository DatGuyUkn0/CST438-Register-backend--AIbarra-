package com.cst438.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	public StudentDTO CreateNewStudent(@RequestBody StudentDTO Newstudent) {
		// to see if Student with prompted email is in the Database
		Student student = studentRepository.findByEmail(Newstudent.email);

		if(student == null) {
			//creates student object to be saved into the studentRepository
			Student New = new Student();
			New.setEmail(Newstudent.email);
			New.setName(Newstudent.name);
			Student Saved = studentRepository.save(New);
			//Calls method to create DTO needed to display new student information upon creation
			StudentDTO studentDTO = createStudentDTO(Saved);
			
			return studentDTO;
		}else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is already in use.");
		}
	}
	// creates new Student DTO
	private StudentDTO createStudentDTO(Student e)
	{
		StudentDTO Newstudent = new StudentDTO();
		Newstudent.student_id = e.getStudent_id();
		Newstudent.email = e.getEmail();
		Newstudent.name = e.getName();
		Newstudent.status = e.getStatus();
		Newstudent.statusCode = e.getStatusCode();
		
		return Newstudent;
	}
	
	@PatchMapping("/student/{email}")
	@Transactional
	public StudentDTO studentHold(@PathVariable String email){
		//Finds Student using email provided
		Student Modstudent = studentRepository.findByEmail(email);
		//Check to see if the student already has a Hold
		//if statusCode = 0 the student does not have a hold
		//the if statement then switches back and forth depending
		//on its current status
		if(Modstudent.getStatusCode() == 0)
		{
			Modstudent.setStatusCode(1);
		}
		else
		{
			Modstudent.setStatusCode(0);
		}
		//Student object then saves changes to the repository
		Student set = studentRepository.save(Modstudent);
		//calls method to return studentDTO to display
		StudentDTO results = createStudentDTO(set);
		
		return results;
	}
}