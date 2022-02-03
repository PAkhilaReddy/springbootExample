package com.springwebexample.SpringWeb.service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springwebexample.SpringWeb.Util.PasswordHashGenerator;
import com.springwebexample.SpringWeb.repository.UserRepository;
import com.springwebexample.SpringWeb.user.User;

@Service
public class UserService {

	public final UserRepository userrepository;

	@Autowired
	public UserService(UserRepository userrepository) {
		this.userrepository = userrepository;
	}

	public List<User> getUsers() {
		return userrepository.findAll();

	}

	public User getUser(Long id) {
		Optional<User> user = userrepository.findById(id);
		if (user.isPresent()) {
			return user.get();
		} else {
			return null;
		}

	}

	public void addUser(User user) {
		String password;
		try {
			password = PasswordHashGenerator.createHash(user.getPassword().toCharArray());
			user.setPassword(password);
			userrepository.save(user);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public void updateUser(Long id, String name, String email, String phoneNumber, String newPassword) {
		Optional<User> userop = userrepository.findById(id);
		if (!userop.isPresent()) {
			throw new IllegalStateException("User does not exist with id" + id);
		}
		User user = userop.get();
		if (name != null && !user.getName().equals(name)) {
			user.setName(name);
		}
		if (email != null && !user.getEmail().equals(email)) {
			User userByEmail = userrepository.findUserByEmail(email);
			if (userByEmail != null) {
				throw new IllegalStateException("email taken. Please try another emailId");
			}
			user.setEmail(email);
		}

		if (phoneNumber != null && !user.getPhoneNumber().equals(phoneNumber)) {
			user.setPhoneNumber(phoneNumber);
		}

		if (newPassword != null) {
			try {
				user.setPassword(PasswordHashGenerator.createHash(newPassword.toCharArray()));
			} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
				throw new IllegalStateException("Exception during password processing");
			}
		}

	}

	public void deleteUser(Long id) {
		boolean b = userrepository.existsById(id);
		if (!b) {
			throw new IllegalStateException("User does not exist with id" + id);
		}
		userrepository.deleteById(id);
	}


}
