package com.example.raresm.sdproject1.repos;

import com.example.raresm.sdproject1.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    User findUserById(Integer id);

    @Override
    Iterable<User> findAll();

    User findUserByUsername(String username);
}
