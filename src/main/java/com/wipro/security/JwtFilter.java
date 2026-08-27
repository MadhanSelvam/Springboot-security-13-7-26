package com.wipro.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.wipro.service.CustomUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tools.jackson.databind.ObjectMapper;

@Component
public class JwtFilter extends OncePerRequestFilter{

	 	@Autowired
	    private CustomUserDetailsService customUserDetailsService;
		@Autowired
		private JwtUtil jwtUtil;
	 
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		
		String authHeader = request.getHeader("Authorization");  //collecting the token
		
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {  //checking it is valid token or not with the parameters
			filterChain.doFilter(request, response);
			return;
		}
		
		String token = authHeader.substring(7);  //storing the token for further processing
		
		try {
			String username = jwtUtil.extractUsername(token);  //username extraction from payload
			
			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null ) {  //checks for username extracted successfully or not ,is user not already authenticated ?
				
				UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);   //spring loads username from DB
				
				if (jwtUtil.validateToken(token, userDetails)) {
					UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), 
																												      userDetails.getPassword(), 
																												      userDetails.getAuthorities());
					
					authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); //adds req info like IP address ,session ID,req details
					
					SecurityContextHolder.getContext().setAuthentication(authenticationToken);
				}		
						
			}
		} catch (Exception e) {
			
			Map<String, String> responseMap = new HashMap<>();   //creating an empty hashmap
			responseMap.put("error", "Invalid Token");			 //preparing a response for postman
			
			ObjectMapper objectMapper = new ObjectMapper(); //objectMapper is a Jackson class,converts java object into JSON and vice verssa ..
			String jsonString = objectMapper.writeValueAsString(responseMap);
			
			response.getWriter().write(jsonString);			//used to return JSON into HTTP response
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);	//displays 401 error
			return;											//stops further execution
		}
		
		filterChain.doFilter(request, response);
		
		  
		
	}

}
