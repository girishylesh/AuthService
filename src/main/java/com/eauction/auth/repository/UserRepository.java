package com.eauction.auth.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.eauction.auth.entity.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    
    User findByIdAndPassword(String userId, String userPassword);
    
}
