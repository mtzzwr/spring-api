package com.mtzzwr.odonto.resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mtzzwr.odonto.dto.UserAccountCreddential;
import com.mtzzwr.odonto.model.User;
import com.mtzzwr.odonto.repository.UserRepository;
import com.mtzzwr.odonto.security.JwtAuthService;

@RestController
@RequestMapping("/odonto")
public class UserAuthentication {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JwtAuthService jwtService;
	
	@Autowired()
	private AuthenticationManager authManager;
	
	@PostMapping("/auth/login")
	public ResponseEntity<Map<Object, Object>> signIn(@RequestBody UserAccountCreddential credential) {
		System.out.println("*** autenticando ***");
		
		try {
			UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(credential.getUsername(), credential.getPassword());
			authManager.authenticate(user);
			List<String> roles = new ArrayList<>();
			User userLogin = new User();
			userLogin = userRepository.findByUsername(credential.getUsername());
			roles.add(userLogin.getRole());
			String token = jwtService.createToken(credential.getUsername(), roles);
			Map<Object, Object> jsonResponse = new HashMap<>();
			jsonResponse.put("username", credential.getUsername());
			jsonResponse.put("token", token);
			return ResponseEntity.ok(jsonResponse);
		} catch (Exception e) {
			System.out.println(e);
		}
		
		return null;
		
	}
}
