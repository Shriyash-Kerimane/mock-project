package com.demo.spring;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.demo.spring.dto.CredentialsDTO;
import com.demo.spring.entity.Users;
import com.demo.spring.util.Message;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class APIGatewayLoginTest {
	
	@LocalServerPort
	int port;
	
	@Autowired
	TestRestTemplate testRestTemplate;
	
	@Test
	void testLogintoCustomer() throws Exception {

		CredentialsDTO input = new CredentialsDTO(10000, "root", "customer");
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		
		HttpEntity<CredentialsDTO> req = new HttpEntity<>(input, headers);
		
		ResponseEntity<Message> response = testRestTemplate.exchange("http://localhost:" + port + "/gateway/",
				HttpMethod.POST, req, Message.class);
		
		Assertions.assertEquals("customer", response.getBody().getStatus());

	}

	@Test
	void testLoginToEmployee() throws Exception {

		CredentialsDTO input = new CredentialsDTO(10005, "welcome1", "employee");
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		
		HttpEntity<CredentialsDTO> req = new HttpEntity<>(input, headers);
		
		ResponseEntity<Message> response = testRestTemplate.exchange("http://localhost:" + port + "/gateway/",
				HttpMethod.POST, req, Message.class);
		
		Assertions.assertEquals("employee", response.getBody().getStatus());

	}

	@Test
	void testLoginPasswordFail() throws Exception {

		CredentialsDTO input = new CredentialsDTO(10005, "root", "employee");
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		
		HttpEntity<CredentialsDTO> req = new HttpEntity<>(input, headers);
		
		ResponseEntity<Message> response = testRestTemplate.exchange("http://localhost:" + port + "/gateway/",
				HttpMethod.POST, req, Message.class);
		
		Assertions.assertEquals("Incorrect username or password", response.getBody().getStatus());

	}

	@Test
	void testLoginUserFail() throws Exception {

		CredentialsDTO input = new CredentialsDTO(100050, "welcome1", "employee");
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		
		HttpEntity<CredentialsDTO> req = new HttpEntity<>(input, headers);
		
		ResponseEntity<Message> response = testRestTemplate.exchange("http://localhost:" + port + "/gateway/",
				HttpMethod.POST, req, Message.class);
		
		Assertions.assertEquals("Incorrect username or password", response.getBody().getStatus());

	}

}
