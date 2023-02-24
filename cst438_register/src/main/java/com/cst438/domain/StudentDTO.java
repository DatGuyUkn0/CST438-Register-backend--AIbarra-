package com.cst438.domain;

import java.util.Objects;

public class StudentDTO {
	public int student_id;
	public String name;
	public String email;
	public String status; 
	public int statusCode;

	
	@Override
	public String toString() {
		return "StudentDTO {student_id=" + student_id + ", name=" + name + ", email=" + email + ", status=" + status
				+ ", statusCode=" + statusCode + "}";
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentDTO other = (StudentDTO) obj;
		return Objects.equals(email, other.email) && Objects.equals(name, other.name)
				&& Objects.equals(status, other.status) && statusCode == other.statusCode
				&& student_id == other.student_id;
	}
	

}
