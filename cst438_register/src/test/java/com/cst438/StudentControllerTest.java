package com.cst438;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.cst438.controller.StudentController;
import com.cst438.domain.Enrollment;
import com.cst438.domain.ScheduleDTO;
import com.cst438.domain.Student;
import com.cst438.domain.StudentDTO;
import com.cst438.domain.StudentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@ContextConfiguration(classes = { StudentController.class })
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest
class StudentControllerTest {
	public static final String Test_Student_Name = "Jonathan Floor" ;
	public static final String Test_Student_Email = "JFloor@csumb.edu";
	public static final String Test_Present_Student_Email = "test@csumb.edu";
	public static final String Test_Present_Student_Name = "test";


	@MockBean
	StudentRepository studentRepository;
	

	@Autowired
	private MockMvc mvc;
	
	@Test
	public void createNewStudent() throws Exception {
		MockHttpServletResponse response;
		Student Newstudent = new Student();
		Newstudent.setEmail(Test_Student_Email);
		Newstudent.setEmail(Test_Student_Email);
		
		given(studentRepository.save(any(Student.class))).willReturn(Newstudent);
		
	    StudentDTO studentDTO = new StudentDTO();
	    studentDTO.email = Test_Student_Email;
	    studentDTO.name = Test_Student_Name;
	    
	    
		response = mvc.perform(
				MockMvcRequestBuilders
			      .post("/student")
			      .content(asJsonString(studentDTO))
			      .contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();
		
		
		
		
		// Returns OK
		assertEquals(200, response.getStatus());
		
		//verify that student has been added and has non-zero id
		StudentDTO result = fromJsonString(response.getContentAsString(), StudentDTO.class);
		assertEquals( 0  , result.student_id);
		
		// verify that repository save method was called
		verify(studentRepository).save(any(Student.class));
		// verify that repository find method was called
		verify(studentRepository, times(1)).findByEmail(Test_Student_Email);
	    
	}
	
	@Test
	public void studentHold() throws Exception{
		MockHttpServletResponse response;
		Student student = new Student();
		student.setEmail(Test_Student_Email);
		student.setName(Test_Student_Name);
		student.setStatusCode(0);
		
		given(studentRepository.save(student)).willReturn(student);

		//Will Confirm that student exists 
	    given(studentRepository.findByEmail(Test_Student_Email)).willReturn(student);
	    
	  
		
		response = mvc.perform(
				MockMvcRequestBuilders
			      .patch("/student/JFloor@csumb.edu"))
			      .andReturn().getResponse();
		
		// verify returns OK
		assertEquals(200, response.getStatus());
		
		StudentDTO result = fromJsonString(response.getContentAsString(), StudentDTO.class);
		assertNotEquals( 0  , result.statusCode);
		
		response = mvc.perform(
				MockMvcRequestBuilders
			      .patch("/student/JFloor@csumb.edu"))
			      .andReturn().getResponse();
		
		// verify returns OK
		assertEquals(200, response.getStatus());
		
		StudentDTO result_2 = fromJsonString(response.getContentAsString(), StudentDTO.class);
		assertNotEquals( 1  , result_2.statusCode);
		
		// verify that repository save method was called
		verify(studentRepository, times(2)).save(any(Student.class));
		// verify that repository find method was called
		verify(studentRepository, times(2)).findByEmail(Test_Student_Email);
	}
	
	
	
	
	
	
	private static String asJsonString(final Object obj) {
		try {

			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static <T> T  fromJsonString(String str, Class<T> valueType ) {
		try {
			return new ObjectMapper().readValue(str, valueType);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
