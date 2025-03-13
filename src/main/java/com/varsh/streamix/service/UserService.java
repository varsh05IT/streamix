package com.varsh.streamix.service;

import com.varsh.streamix.model.Role;
import com.varsh.streamix.model.UserDetails;
import com.varsh.streamix.repository.UserRepository;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public String addNewUser(final UserDetails userDetails) {

        if (userRepository.existsByUserEmailId(userDetails.getUserEmailId())) {
            return "User already exists";
        }
        
        boolean isAdminExists = userRepository.existsByRole(Role.ADMIN);
        if(!isAdminExists) {
            userDetails.setRole(Role.ADMIN);
        } else {
            userDetails.setRole(Role.USER);
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
        if (user == null) {
            System.out.println("User not found with email: " + userDetails.getUserEmailId());
            return "User not found";
        }

        if (!passwordEncoder.matches(userDetails.getUserPassword(), user.getUserPassword())) {
            System.out.println("Invalid credentials for user: " + userDetails.getUserEmailId());
            return "Invalid credentials";
        }
        return (user.getRole() == Role.ADMIN)? "ADMIN" : "USER";
    }

    public List<UserDetails> getAllUsers() {
        return userRepository.findAll();
    }

    public void updateUserRole(Long id, Role role) {
        UserDetails user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setRole(role);
            userRepository.save(user);
        }
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

//    @Transactional
//    public void updateUser(UserDetails user) {
//        UserDetails existingUser = userRepository.findById(user.getId()).orElse(null);
//        if (existingUser != null) {
//            existingUser.setUserName(user.getUserName());
//            existingUser.setUserEmailId(user.getUserEmailId());
//            existingUser.setUserPhoneNo(user.getUserPhoneNo());
//            existingUser.setRole(user.getRole());
//            userRepository.save(existingUser); 
//        }
//    }
    
    @Transactional
    public void updateUser(UserDetails user) {
        UserDetails existingUser = userRepository.findById(user.getId()).orElse(null);
        if (existingUser != null) {
            existingUser.setUserName(user.getUserName());
            existingUser.setUserEmailId(user.getUserEmailId());
            existingUser.setUserPhoneNo(user.getUserPhoneNo());
            existingUser.setRole(user.getRole());
            if (user.getUserPassword() != null && !user.getUserPassword().isEmpty()) {
                String encryptedPassword = passwordEncoder.encode(user.getUserPassword());
                existingUser.setUserPassword(encryptedPassword);
            }
            userRepository.save(existingUser);
        }
    }

    public UserDetails getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

}
