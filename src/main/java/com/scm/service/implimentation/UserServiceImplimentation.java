package com.scm.service.implimentation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scm.entities.User;
import com.scm.helper.ResourceNotFoundException;
import com.scm.repository.UserRepository;
import com.scm.service.UserService;

@Service
public class UserServiceImplimentation implements UserService {

    // create object of UserRepository to user its methods
    @Autowired
    UserRepository userRepository;

    // To save user data in DB
    @Override
    public User saveUser(User user) {
        // generating userId
        String userId=UUID.randomUUID().toString();
        // add userId to the user
        user.setUserId(userId);
        // save user to DB.
        return userRepository.save(user);
    }

    // To get user details from DB by ID.
    @Override
    public Optional<User> GetUserById(String id) {
        return userRepository.findById(id);
    }

    // To update user details to DB.
    @Override
    public Optional<User> updateUser(User user) {
        // First find the user from db
        User user2 = userRepository.findById(user.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));

        // update user detail of DB user from the new user.
        user2.setName(user.getName());
        user2.setEmail(user.getEmail());
        user2.setPassword(user.getPassword());
        user2.setAbout(user.getAbout());
        user2.setPhoneNumber(user.getPhoneNumber());
        user2.setProfilePicLink(user.getProfilePicLink());
        user2.setEnable(user.isEnable());
        user2.setEmailVerified(user.isEmailVerified());
        user2.setPhoneVerified(user.isPhoneVerified());
        user2.setProvider(user.getProvider());
        user2.setProviderUserId(user.getProviderUserId());
        // Save this user to DB
        userRepository.save(user2);
        return Optional.ofNullable(user2);
    }

    // To delete user from the DB
    @Override
    public void deleteUser(String id) {
        // First find user if exist
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException());
        // If user exist then delete the user
        userRepository.delete(user);
    }

    // find user by userid
    @Override
    public boolean isUserExist(String userId) {
        User user = userRepository.findById(userId).orElse(null);

        return (user != null) ? true : false;
    }

    // Find user by email.
    @Override
    public boolean isUserExistByEmail(String email) {
        User user = userRepository.findByEmail(email).orElse(null);

        return (user != null) ? true : false;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}
