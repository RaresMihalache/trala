package com.example.raresm.sdproject1.services;

import com.example.raresm.sdproject1.exceptions.UserIdException;
import com.example.raresm.sdproject1.model.Device;
import com.example.raresm.sdproject1.model.User;
import com.example.raresm.sdproject1.repos.DeviceRepository;
import com.example.raresm.sdproject1.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    public User saveOrUpdateUser(User user){
        // Logic

        return userRepository.save(user);
    }

    public User findByUserId(String userId){
        Integer userIdLong = Integer.parseInt(userId);
        User user = userRepository.findUserById(userIdLong);
        if(user == null){
            throw new UserIdException("User Id: '" + userId + "' does not exist");
        }

        return user;
    }

    public Iterable<User> findAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUserById(String userId){
        Integer userIdLong = Integer.parseInt(userId);
        User user = userRepository.findUserById(userIdLong);
        if(user == null){
            throw new UserIdException("Cannot delete User with Id: " + userId +"'. This user ID does not exist.");
        }

        userRepository.delete(user);
    }

}
