package com.eauction.auth.entity;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.eauction.auth.enums.UserRole;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Document("User")
public class User {
	@Id
	private String id;
	private String password;
	private UserRole role;
	private LocalDate createDate;
}
