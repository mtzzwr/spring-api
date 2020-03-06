package com.mtzzwr.odonto.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtAuthConfigurer extends 
SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

	private JwtAuthService jwtAuthService;
	
	public JwtAuthConfigurer(JwtAuthService jwtAuthService) {
		this.jwtAuthService = jwtAuthService;
	}

	@Override
	public void configure(HttpSecurity builder) throws Exception {
		JwtAuthFilter jwtAuthFilter = new JwtAuthFilter(jwtAuthService);
		builder.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
	}
}
