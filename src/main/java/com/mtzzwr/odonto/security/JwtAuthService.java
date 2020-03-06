package com.mtzzwr.odonto.security;

import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtAuthService {

	private static final String SECRETKEY = Base64.getEncoder().encodeToString("Senai127".getBytes());
	private static final String PREFIX = "Bearer";
	private static final String EMPTY = "";
	private static final String AUTHORIZATION = "Authorization";
	private static final long EXPIRATIONTIME = 86400000;
	
	@Autowired
	private UserDetailServiceImpl userDetailService;
	
	public String createToken(String username, List<String> roles) {
		
		Claims claims = Jwts.claims().setSubject(username);
		claims.put("roles", roles);
		
		Date now = new Date();
		Date validatity = new Date(now.getTime() + EXPIRATIONTIME);
		
		String token = Jwts.builder().setClaims(claims).setIssuedAt(now).setExpiration(validatity).signWith(SignatureAlgorithm.HS512, SECRETKEY).compact();
		
		return token;
	}
	
	public Authentication getAuthentication(HttpServletRequest req) {
	
		String token = resolveToken(req);
		if(token != null && validateToken(token)) {
			String username = Jwts.parser().setSigningKey(SECRETKEY).parseClaimsJws(token).getBody().getSubject();
			
			if(username != null) {
				UserDetails userDetails = userDetailService.loadUserByUsername(username);
				return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				
			}
		}
		
		return null;
		
	}
	
	private boolean validateToken(String token) {
		Jws<Claims> claims = Jwts.parser().setSigningKey(SECRETKEY).parseClaimsJws(token);
		
		if(claims.getBody().getExpiration().before(new Date())) {
			return false;
		}
		
		return true;
	}
	
	public String resolveToken(HttpServletRequest req) {
		String bearerToken = req.getHeader(AUTHORIZATION);
		if(bearerToken != null && bearerToken.startsWith(PREFIX)) {
			return bearerToken.replace(PREFIX, EMPTY).trim();
		}
		
		return null;
	}
	
}
