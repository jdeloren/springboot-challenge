package com.mindex.challenge;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ChallengeApplicationTests {

	private String employeeUrl;
	private String employeeIdUrl;
	private String reportIdUrl;

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Before
	public void setup() {
		employeeUrl = "http://localhost:" + port + "/employee";
		employeeIdUrl = "http://localhost:" + port + "/employee/{id}";

		reportIdUrl = "http://localhost:" + port + "/reports/{id}";
	}

	@Test
	public void contextLoads() {
		Employee testEmployee = new Employee();
		testEmployee.setFirstName("Norman");
		testEmployee.setLastName("Chapman");
		testEmployee.setDepartment("Engineering");
		testEmployee.setPosition("Developer");

		// Create checks
		Employee createdEmployee = restTemplate.postForEntity(employeeUrl, testEmployee, Employee.class).getBody();

		assertNotNull(createdEmployee.getEmployeeId());
		assertEmployeeEquivalence(testEmployee, createdEmployee);

		// Read checks
		Employee readEmployee = restTemplate
				.getForEntity(employeeIdUrl, Employee.class, "62c1084e-6e34-4630-93fd-9153afb65309").getBody();
		assertEquals("Best", readEmployee.getLastName());

		// Update direct reports
		readEmployee.setDirectReports(Arrays.asList(testEmployee));
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		restTemplate.exchange(employeeIdUrl,
				HttpMethod.PUT,
				new HttpEntity<Employee>(readEmployee, headers),
				Employee.class,
				readEmployee.getEmployeeId()).getBody();

		// Report checks
		ReportingStructure manager = restTemplate
				.getForEntity(reportIdUrl, ReportingStructure.class, "16a596ae-edd3-4847-99fe-c4518e82c86f").getBody();
		assertEquals(new Integer(5), manager.getNumberOfReports());
	}

	// support

	private static void assertEmployeeEquivalence(Employee expected, Employee actual) {
		assertEquals(expected.getFirstName(), actual.getFirstName());
		assertEquals(expected.getLastName(), actual.getLastName());
		assertEquals(expected.getDepartment(), actual.getDepartment());
		assertEquals(expected.getPosition(), actual.getPosition());
	}
}
