package com.eauction.auth.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eauction.auth.entity.User;
import com.eauction.auth.exception.UserAlreadyExistsException;
import com.eauction.auth.exception.UserNotFoundException;
import com.eauction.auth.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public User findByUserIdAndPassword(String userId, String password) throws UserNotFoundException {

		User user = userRepository.findByIdAndPassword(userId, password);
		if (user == null) {
			throw new UserNotFoundException("User not found");
		}
		return user;
	}

	public User getUserById(String userId) throws UserNotFoundException {
		try {
			Optional<User> user = userRepository.findById(userId);
			if (user == null || !user.isPresent()) {
				throw new UserNotFoundException("user not found");
			}
			return user.get();
		} catch (Exception ex) {
			throw new UserNotFoundException("user not found");
		}
	}

	public boolean saveUser(User user) throws UserAlreadyExistsException {
		try {

			Optional<User> existingUser = userRepository.findById(user.getId());
			if (existingUser != null && existingUser.isPresent()) {
				throw new UserAlreadyExistsException("User already exist");
			}
			userRepository.save(user);
		} catch (Exception ex) {
			throw new UserAlreadyExistsException("User already exist");
		}
		return true;
	}

	public User updateUser(String userId, User user) throws UserNotFoundException {
		if (userRepository.existsById(userId)) {
			return userRepository.save(user);
		}
		return user;
	}

	public boolean deleteUser(String userId) throws UserNotFoundException {
		userRepository.deleteById(userId);
		return true;
	}
}
