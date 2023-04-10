package com.cst438.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import com.cst438.domain.EnrollmentDTO;

public class GradebookServiceREST extends GradebookService {

	private RestTemplate restTemplate = new RestTemplate();

	@Value("${gradebook.url}")
	String gradebook_url;
	
	public GradebookServiceREST() {
		System.out.println("REST grade book service");
	}

	@Override
	public void enrollStudent(String student_email, String student_name, int course_id) {
		//Create enrollment object
		EnrollmentDTO enrollment = new EnrollmentDTO();
		enrollment.course_id = course_id;
		enrollment.studentEmail = student_email;
		enrollment.studentName = student_name;
		//Display action and New Enrollment information
		System.out.println("Post to gradebook "+enrollment);
		//Post object in gradebook
		EnrollmentDTO response = restTemplate.postForObject(gradebook_url+"/enrollment", enrollment, EnrollmentDTO.class);
		//Confirmation
		System.out.println("Response from gradebook "+response);
	}
}
