package com.eauction.auth.entity;

import java.time.LocalDateTime;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConvertedEnum;
import com.eauction.auth.converter.LocalDateTimeConverter;
import com.eauction.auth.enums.UserRole;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@DynamoDBTable(tableName = "authuser")
public class AuthUser {
	@DynamoDBHashKey
    @DynamoDBAutoGeneratedKey
	private String userId;
	@DynamoDBAttribute
	private String userPassword;
	@DynamoDBAttribute
	@DynamoDBTypeConvertedEnum
	private UserRole userRole;
	@DynamoDBAttribute
	@DynamoDBTypeConverted(converter = LocalDateTimeConverter.class)
	private LocalDateTime createDateTime;
}