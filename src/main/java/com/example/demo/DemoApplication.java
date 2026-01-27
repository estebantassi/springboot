package com.example.demo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@RestController
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/hello")
	public Map<String, String> hello(@RequestParam(value = "name", defaultValue = "World") String name) {
		Map<String, String> response = new HashMap<>();
		response.put("message", "Hello " + name);
		response.put("code", "200");
		return response;
	}
}