package com.chandebois.repositories;

import com.chandebois.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by nonok on 08/06/2016.
 */
@Repository
public interface UserRepository extends MongoRepository<User, String> {

    User findByNickName(String acs_test);
}
