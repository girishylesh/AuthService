package com.eauction.auth.converter;

import java.time.LocalDateTime;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;

public class LocalDateTimeConverter implements DynamoDBTypeConverter<String, LocalDateTime> {

	@Override
	public String convert(LocalDateTime time) {
		return time.toString();
	}

	@Override
	public LocalDateTime unconvert(String time) {
		return LocalDateTime.parse(time);
	}

}
