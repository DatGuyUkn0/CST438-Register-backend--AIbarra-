package com.cst438.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	public StudentDTO CreateNewStudent(@RequestBody StudentDTO Newstudent) {
		
		
		String present = Newstudent.email;
		Student student = studentRepository.findByEmail(present);
		
		System.out.println("Student object:" + student );
		if(student == null) {
			Student New = new Student();
			New.setEmail(Newstudent.email);
			New.setName(Newstudent.name);
			Student Saved = studentRepository.save(New);
			
			StudentDTO studentDTO = createStudentDTO(Saved);
			System.out.println("New student has been added!");
			return studentDTO;
		}else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is already in use.");
		}
	}
	
	
	private StudentDTO createStudentDTO(Student e)
	{
		StudentDTO Newstudent = new StudentDTO();
		Newstudent.student_id =e.getStudent_id();
		Newstudent.email = e.getEmail();
		Newstudent.name = e.getName();
		Newstudent.status = e.getStatus();
		Newstudent.statusCode = e.getStatusCode();
		
		return Newstudent;
		
	}
	
//	@GetMapping("/student")
//	
//	@DeleteMapping("/student/{student_id}")
//	@Transactional
//	public void DeleteStudent(@RequestParam("student_id") String student_id){
//		
//	}

}
