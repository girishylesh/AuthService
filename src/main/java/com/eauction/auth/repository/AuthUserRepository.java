package com.eauction.auth.repository;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import com.eauction.auth.entity.AuthUser;

@EnableScan
public interface AuthUserRepository extends CrudRepository<AuthUser, String> {
    
    AuthUser findByUserIdAndUserPassword(String userId, String userPassword);
    
}
