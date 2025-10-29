package com.ecommerce.user.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.user.models.User;



@Repository
//For mongodb we removed jpaRepository
public interface UserRepository extends MongoRepository<User,String>{

}
