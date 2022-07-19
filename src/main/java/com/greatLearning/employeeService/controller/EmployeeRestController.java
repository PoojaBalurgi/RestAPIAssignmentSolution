package com.greatLearning.employeeService.controller;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greatLearning.employeeService.entity.Employee;
import com.greatLearning.employeeService.entity.Role;
import com.greatLearning.employeeService.entity.User;
import com.greatLearning.employeeService.service.EmployeeService;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {

	@Autowired
	private EmployeeService employeeService;

	// To add roles in the database dynamically
	@PostMapping("/role")
	public Role saveRole(@RequestBody Role role) {
		return employeeService.saveRole(role);
	}
	
	// To add User in the database which can be used for authentication purposes.
	@PostMapping("/user")
	public User saveUser(@RequestBody User user) {
		return employeeService.saveUser(user);
	}

	// To add employees data in the database if and only if the authenticated user is ADMIN	
	@PostMapping("/employees/addemp")
	public Employee addEmployee(@RequestBody Employee employee) {
		employeeService.save(employee);
		return employee;
	}
		
	// To list all the employees stored in the database.	
	@GetMapping("/employees/allemp")
	public List<Employee> findAll() {
		return employeeService.findAll();
	}

	// To get an employee record specifically based on the id of that employee	
	@GetMapping("/employees/findbyid/{employeeId}")
	public Employee getEmployee(@PathVariable int employeeId) {

		Employee employee = employeeService.findById(employeeId);

		if (employee == null) {
			throw new RuntimeException("Employee id not found - " + employeeId);
		}

		return employee;
	}

	// Update an existing employee		
	@PutMapping("/employees/update/{employeeId}")
	public Employee updateEmployee(@PathVariable int employeeId, @RequestBody Employee employee) {

		Employee emp= employeeService.findById(employeeId);
		emp.setFirstName(employee.getFirstName());
		emp.setLastName(employee.getLastName());
		emp.setEmail(employee.getEmail());
		employeeService.save(emp);
		return emp;
	}

	// To delete an existing employee record based on the id of the employee.	
	@DeleteMapping("/employees/delete/{employeeId}")

	public String deleteEmployee(@PathVariable int employeeId) {
		
		Employee employee = employeeService.findById(employeeId);
		if (employee == null) {
			throw new RuntimeException("Employee id not found - " + employeeId);
		}
		employeeService.deleteById(employeeId);
		return "Deleted employee id - " + employeeId;
	}

	// To fetch an employee by his/her first name and if found more than one record then list them all  
	@GetMapping("/employees/search/{firstName}")
	public List<Employee> searchByFirstName(@PathVariable String firstName) {
		return employeeService.searchByFirstName(firstName);
	}

	// To list all employee records sorted on their first name in either ascending order or descending order
	@GetMapping("/employees/sort")
	public List<Employee> sortbyname(Direction order)
	{
		return employeeService.sortedList(order);
	}

}
