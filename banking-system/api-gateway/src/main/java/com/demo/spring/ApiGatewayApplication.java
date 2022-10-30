package com.demo.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}
	
	@Bean
	public RestTemplate restTemplate1() {
		return new RestTemplate();
	}
	
	@Bean
	OpenAPI openApoDoc() {
		return new OpenAPI().info(new Info().description("Mock project on ABC")
				.license(new License().name("free for all"))
				.title("Mock project document")
				.version("1.0.0-BETA"));
		}
}
