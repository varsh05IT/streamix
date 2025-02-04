package com.varsh.streamix.service;

import com.varsh.streamix.model.UserDetails;
import com.varsh.streamix.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserServiceImpl {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public String addNewUser(final UserDetails userDetails){

        if (userRepository.existsByUserEmailId(userDetails.getUserEmailId())) {
            return "User already exists";
        }
        userDetails.setUserPassword(passwordEncoder.encode(userDetails.getUserPassword()));
        UserDetails userAdded = userRepository.save(userDetails);
        if (userAdded != null) {
            return "User registered successfully";
        } else {
            return "Failed to register user";
        }
    }
    
    public String login(UserDetails userDetails) {
        UserDetails user = userRepository.findUserByEmailId(userDetails.getUserEmailId());
        if(user == null) {
            return "User not found";
        }
        if(!passwordEncoder.matches(userDetails.getUserPassword(), user.getUserPassword())) {
            return "Invalid credentials";
        }
        return "Login successful";
    }
}
