package com.springwebexample.SpringWeb.controller;

import com.springwebexample.SpringWeb.user.User;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springwebexample.SpringWeb.service.UserService;

@RestController
@RequestMapping(path = "/api/v1/user")
public class UserController {

	private final UserService userservice;

	@Autowired
	public UserController(UserService userservice) {
		this.userservice = userservice;
	}

	@GetMapping
	public List<User> getUsers() {
		return userservice.getUsers();
	}
	
	@GetMapping(path = "{userId}")
	public User getUser( @PathVariable("userId") Long id) {
		return userservice.getUser(id);
	}

	@PostMapping
	public void addUser(@RequestBody User user) {
		userservice.addUser(user);
	}
	
	//rest password should be moved to separate functionality and with one-one relationship for USer and Password table.

	@PutMapping(path = "{userId}")
	public void updateUser(@PathVariable("userId") Long id,@RequestBody User user) {
		userservice.updateUser(id, user.getName(), user.getEmail(),user.getPhoneNumber(),user.getPassword());
	}

	@DeleteMapping(path = "{userId}")
	public void deleteMapping(@PathVariable("userId") Long id) {
		userservice.deleteUser(id);
	}
	
	

}
