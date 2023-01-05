package com.eshoppingzone.productservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.eshoppingzone.productservice.entity.User1;

@Repository
public interface UserRepository extends MongoRepository<User1, String>{

 

    User1 findByUsername(String username);
}