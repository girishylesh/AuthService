package com.eauction.auth.controller;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eauction.auth.entity.User;
import com.eauction.auth.enums.UserRole;
import com.eauction.auth.exception.UserAlreadyExistsException;
import com.eauction.auth.exception.UserNotFoundException;
import com.eauction.auth.service.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping("/e-auction/api/v1/auth")
public class AuthController {
	
	@Value("${jwt.secret}")
	private String jwtSecret;

	@Value("${jwt.token.validity}")
	private long tokenValidity;
	
	Map<String, String> map = new HashMap<>();

	@Autowired
	private UserService userService;

	/**
	 * Method to register user
	 * 
	 * @param user User to register
	 * @return Response Status
	 */
	@PostMapping("/register")
	public ResponseEntity<User> registerUser(@RequestBody User user) {
		try {
			user.setUserRole(Optional.ofNullable(user.getUserRole()).orElse(UserRole.USER));
			user.setCreateDate(LocalDate.now());
			userService.saveUser(user);
			return new ResponseEntity<User>(user, HttpStatus.CREATED);
		} catch (UserAlreadyExistsException unfe) {
			return new ResponseEntity<User>(HttpStatus.CONFLICT);
		}
	}

	/**
	 * Method to login
	 * 
	 * @param user User credentials
	 * @return Token Map
	 * @throws ServletException
	 */
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody User user) throws ServletException {
		String jwtToken = "";
		try {
			jwtToken = getToken(user.getUserId(), user.getUserPassword());
			map.clear();
			map.put("message", "Login successfull");
			map.put("currentUser", user.getUserId());
			map.put("token", jwtToken);
		} catch (Exception e) {
			String exceptionMessage = e.getMessage();
			map.clear();
			map.put("token", null);
			map.put("message", exceptionMessage);
			return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
		}
		return new ResponseEntity<>(map, HttpStatus.OK);
	}

	/**
	 * Generate JWT token
	 * 
	 * @param username User provided name
	 * @param password User provided password
	 * @return Token
	 * @throws Exception
	 */
	public String getToken(String username, String password) throws Exception {

		if (username == null || password == null) {
			throw new ServletException("Please fill in username and password");
		}
		User user = null;
		String jwtToken = null;
		try {
			user = userService.findByUserIdAndPassword(username, password);
			jwtToken = Jwts.builder().setSubject(user.getUserId()).setIssuedAt(new Date())
					.setExpiration(new Date(System.currentTimeMillis() + tokenValidity))
					.signWith(SignatureAlgorithm.HS256, jwtSecret).compact();
		} catch (UserNotFoundException unfe) {
			throw new ServletException("Invalid credentials.");
		}

		return jwtToken;
	}
}
