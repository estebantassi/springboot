package com.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.database.user.User;
import com.database.user.UserRepository;
import com.tools.EmailHasher;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/signup")
public class SignupController {

    private final UserRepository userRepository;
    private final EmailHasher emailHasher;

    public SignupController(UserRepository userRepository, EmailHasher emailHasher) {
        this.userRepository = userRepository;
        this.emailHasher = emailHasher;
    }

    public static class SignupRequest {
        @NotBlank private String email;
        @NotBlank private String password;

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }
    
	@PostMapping
	public Map<String, String> signup(@Valid @RequestBody SignupRequest req) {

        String email = req.getEmail();
        String password = req.getPassword();

		String hashedEmail = emailHasher.hashEmail(email);
		User newuser = new User(hashedEmail, password);

		Map<String, String> response = new HashMap<>();
		try {
			userRepository.save(newuser);
			userRepository.flush();
			response.put("message", "Successfully created account");
			response.put("code", "200");
		} catch (Exception e) {
			response.put("message", "Account creation failed");
			response.put("code", "400");
			e.printStackTrace();
		}

		return response;
	}
}