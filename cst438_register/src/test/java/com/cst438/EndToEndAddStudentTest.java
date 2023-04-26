package com.cst438;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cst438.domain.Enrollment;
import com.cst438.domain.Student;
import com.cst438.domain.StudentRepository;

@SpringBootTest
public class EndToEndAddStudentTest {

	public static final String EDGE_DRIVER_FILE_LOCATION = "C:/Users/alfre/Downloads/edgedriver_win64/msedgedriver.exe";
	public static final String URL = "http://localhost:3000";
	public static final String TEST_USER_EMAIL = "Jtest@csumb.edu";
	public static final String TEST_USER_NAME = "John Test";
	public static final int SLEEP_DURATION = 1000; // 1 second.
	
	@Autowired
	StudentRepository studentRepository;
	
	@Test
	public void newStudent() throws Exception {
		//this will check to see if student in test exists in database and deletes them before test begins
		Student e = null;
		do {
			e = studentRepository.findByEmail(TEST_USER_EMAIL);
			if (e != null)
				studentRepository.delete(e);
		} while (e != null);
		//this identifies and confirms browser connection
		System.setProperty("webdriver.edge.driver", EDGE_DRIVER_FILE_LOCATION);
		WebDriver driver = new EdgeDriver();
		// Puts an Implicit wait for 10 seconds before throwing exception
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		try {
			driver.get(URL);
			Thread.sleep(SLEEP_DURATION);
			//clicks button to add a student
			driver.findElement(By.id("addStudent")).click();
			Thread.sleep(SLEEP_DURATION);
			//this finds textfield for name then uses sendKeys to input test info for name
			WebElement studentName = driver.findElement(By.id("studentname"));
			studentName.sendKeys(TEST_USER_NAME);
			//this finds textfield for email then uses sendKeys to input test info email
			WebElement studentEmail = driver.findElement(By.id("studentemail"));
			studentEmail.sendKeys(TEST_USER_EMAIL);
			//this finds and clicks button to add student to database
			driver.findElement(By.id("add")).click();
			Thread.sleep(SLEEP_DURATION);
			//this check determines if student has been found to be added to the database or is null
			Student check = studentRepository.findByEmail(TEST_USER_EMAIL);
			//asserts that student information is in database
			assertNotNull(check,"Student not found in database");
		} catch (Exception ex) {
			throw ex;
		} finally {
			// clean up database.
			Student r = studentRepository.findByEmail(TEST_USER_EMAIL);
			if (r != null)
				studentRepository.delete(r);
			driver.quit();
		} 
	}
}