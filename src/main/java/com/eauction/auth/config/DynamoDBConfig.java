package com.eauction.auth.config;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;

import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableDynamoDBRepositories(basePackages = "com.eauction.auth.repository")
public class DynamoDBConfig {

	@Value("${aws.dynamodb.endpoint.url}")
	private String awsDynamoDBEndpoint;

	@Value("${aws.access.key}")
	private String awsAWSAccessKey;

	@Value("${aws.access.secretkey}")
	private String awsAWSSecretKey;

	@Value("${aws.region}")
	private String awsRegion;

	@Bean
	public AmazonDynamoDB amazonDynamoDB(AWSCredentialsProvider awsCredentialsProvider) {
		AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder.standard()
				.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(awsDynamoDBEndpoint, awsRegion))
				.withCredentials(awsCredentialsProvider).build();
		return amazonDynamoDB;
	}

	@Bean
	public AWSCredentialsProvider awsCredentialsProvider() {
		return new AWSStaticCredentialsProvider(new BasicAWSCredentials(awsAWSAccessKey, awsAWSSecretKey));
	}

}