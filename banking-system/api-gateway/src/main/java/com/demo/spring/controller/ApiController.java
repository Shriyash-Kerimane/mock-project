package com.demo.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.demo.spring.dto.CredentialsDTO;
import com.demo.spring.dto.ServerConfiguration;
import com.demo.spring.entity.Customer;
import com.demo.spring.entity.RegisterInput;
import com.demo.spring.entity.Users;
import com.demo.spring.service.ApiService;
import com.demo.spring.util.Message;

import io.swagger.v3.oas.annotations.OpenAPI30;

@RestController
@OpenAPI30
@EnableConfigurationProperties(ServerConfiguration.class)
@RequestMapping(path = "/gateway")
public class ApiController {
	
	@Autowired
	ApiService apiService;
	
	@Autowired
	ServerConfiguration servers;
	
	@Autowired
	RestTemplate restTemplate;
	
	@PostMapping(path = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> registerUser(@RequestBody RegisterInput input) {
		
		Users user = new Users(input.getUserName(), input.getPassword(), input.getUser());
		Message message = apiService.saveCredential(user);
		
		if(input.getUser().equals("customer")) {
	        HttpHeaders headers= new HttpHeaders();
	        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
	        headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);    
	        
	        Customer customer = new Customer(user.getUserId(), input.getUserName(), input.getPhoneNumber(), input.getEmailId());
	        
	        HttpEntity<Customer> req = new HttpEntity<>(customer, headers);
	        ResponseEntity<Message> response=restTemplate.exchange(servers.getEmployeeServer()+"/employee/savecustomer", HttpMethod.POST,req, Message.class);
	        return ResponseEntity.ok(response.getBody());
		}
		return ResponseEntity.ok(message);
	}
	
	@PostMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Message> login(@RequestBody CredentialsDTO credentialsDTO) {
        return ResponseEntity.ok(apiService.checkLogin(credentialsDTO));
    }
	
}
	

