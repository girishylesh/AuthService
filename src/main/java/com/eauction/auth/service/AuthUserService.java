package com.eauction.auth.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eauction.auth.entity.AuthUser;
import com.eauction.auth.exception.UserAlreadyExistsException;
import com.eauction.auth.exception.UserNotFoundException;
import com.eauction.auth.repository.AuthUserRepository;

@Service
public class AuthUserService {

	@Autowired
	private AuthUserRepository userRepository;

	public AuthUser findByUserIdAndPassword(String userId, String password) throws UserNotFoundException {

		AuthUser user = userRepository.findByUserIdAndUserPassword(userId, password);
		if (user == null) {
			throw new UserNotFoundException("User not found");
		}
		return user;
	}

	public AuthUser getUserById(String userId) throws UserNotFoundException {
		try {
			Optional<AuthUser> user = userRepository.findById(userId);
			if (user == null || !user.isPresent()) {
				throw new UserNotFoundException("user not found");
			}
			return user.get();
		} catch (Exception ex) {
			throw new UserNotFoundException("user not found");
		}
	}

	public boolean saveUser(AuthUser user) throws UserAlreadyExistsException {
		try {

			Optional<AuthUser> existingUser = userRepository.findById(user.getUserId());
			if (existingUser != null && existingUser.isPresent()) {
				throw new UserAlreadyExistsException("User already exist");
			}
			userRepository.save(user);
		} catch (Exception ex) {
			throw new UserAlreadyExistsException("User already exist");
		}
		return true;
	}

	public AuthUser updateUser(String userId, AuthUser user) throws UserNotFoundException {
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
