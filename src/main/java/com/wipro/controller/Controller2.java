package com.wipro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@ResponseBody
public class Controller2 {

	@GetMapping
	public String getHomePage() {
		return "Wecome !!!!!!!!!!!!!!!!";
	}
	
	@GetMapping("/dashboard")
	public String getDashboard() {
		return "Login success !!!!!!!!!!!!";
	}
	
	
}
