package com.demo.spring;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import com.demo.spring.entity.RegisterInput;
import com.demo.spring.entity.Users;
import com.demo.spring.repository.ApiRepository;
import com.demo.spring.util.Message;
import com.fasterxml.jackson.databind.ObjectMapper;;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT,properties = {
		"employeeServer=http://localhost:${wiremock.server.port}"})
@AutoConfigureWireMock(port = 0)
@AutoConfigureMockMvc
class APIGatewayTest {
	
	String employeeServer="http://localhost:${wiremock.server.port}";

	@LocalServerPort
	int port;
	
	@Autowired
	TestRestTemplate testRestTemplate;

	@Test
	void testRegisterUser() throws Exception{
		Users user = new Users("abc", "root", "customer");
		user.setUserId(12345);
		Message message = new Message("Customer save successfully");
		
		RegisterInput input = new RegisterInput("abc", "root", "999", "abc@def.com", "customer");
		
		ObjectMapper mapper = new ObjectMapper();
		String messageJson = mapper.writeValueAsString(message);
		
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		
		HttpEntity<RegisterInput> req = new HttpEntity<>(input, headers);
		
		stubFor(post(urlEqualTo("/employee/savecustomer"))
				.willReturn(aResponse().withHeader("Content-Type", "application/json").withBody(messageJson)));
		ResponseEntity<Message> response = testRestTemplate.exchange("http://localhost:" + port + "/gateway/register",
				HttpMethod.POST, req, Message.class);
		Assertions.assertEquals(message.getStatus(), response.getBody().getStatus());
	}
	
	@Test
	void testRegisterEmployee() throws Exception{
		Users user = new Users("abc", "root", "employee");
		user.setUserId(12345);
		Message message = new Message("User credential saved");
		
		RegisterInput input = new RegisterInput("abc", "root", "999", "abc@def.com", "employee");
		
		ObjectMapper mapper = new ObjectMapper();
		String messageJson = mapper.writeValueAsString(message);
		
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		
		HttpEntity<RegisterInput> req = new HttpEntity<>(input, headers);
		
		stubFor(post(urlEqualTo("/employee/savecustomer"))
				.willReturn(aResponse().withHeader("Content-Type", "application/json").withBody(messageJson)));
		ResponseEntity<Message> response = testRestTemplate.exchange("http://localhost:" + port + "/gateway/register",
				HttpMethod.POST, req, Message.class);
		Assertions.assertEquals(message.getStatus(), response.getBody().getStatus());
	}
	
	
	
	
	
	
//	@Test
//	void testAddAppointmentSuccess() throws Exception {
//
//		AppointmentDTO appointment = new AppointmentDTO(1, 3, 3, "2022-10-14");
//		DoctorDTO doctorDTO = new DoctorDTO(3, "sawad", "m", "vrh@gmail.com");
//		PatientDTO patientDTO = new PatientDTO(3, "vishwa", "rh", "vrh@gmail.com");
//
//		ObjectMapper mapper = new ObjectMapper();
//		String doctorJson = mapper.writeValueAsString(doctorDTO);
//		String patientJson = mapper.writeValueAsString(patientDTO);
//
//		Message message = new Message("Appointment Booked..");
//		String messageJson = mapper.writeValueAsString(message);
//
//		HttpHeaders headers = new HttpHeaders();
//		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
//
//		HttpEntity<AppointmentDTO> req = new HttpEntity<>(appointment, headers);
//
//		stubFor(get(urlEqualTo("/clinic/doctor/find/3"))
//				.willReturn(aResponse().withHeader("Content-Type", "application/json").withBody(doctorJson)));
//		stubFor(get(urlEqualTo("/patient/3"))
//				.willReturn(aResponse().withHeader("Content-Type", "application/json").withBody(patientJson)));
//
//		ResponseEntity<String> response = testRestTemplate.exchange("http://localhost:" + port + "/appointment/",
//				HttpMethod.POST, req, String.class);
//
//		Assertions.assertEquals(messageJson, response.getBody());
//	}

//	void testRegisterUserPass() {
//		
//		RegisterInput user = new RegisterInput("shriyash", "abc", "123", "abc@def.com", "customer");
//		
//		Customer customer = new Customer(100, "shriyash", "123", "abc@def.com");
//		
//		String userJson = new ObjectMapper().writeValueAsString(user);
//		String customerJson = new ObjectMapper().writeValueAsString(customer);
//		
//		stubFor(post(urlEqualTo("/employee/savecustomer")).withRequestBody(equalToJson(customerJson,true,true));
//				
//				.willReturn(aResponse())
//					.withHeader("Content-Type", "application/json").withBody(userJson)));
//		
//	}

}
