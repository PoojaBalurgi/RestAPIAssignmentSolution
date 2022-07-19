package com.greatLearning.employeeService.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

	@GetMapping("/")
	public String home() {
		return("<h1> Graded Project - Employee Management Rest Api based Web application</h1>");
	}
}
