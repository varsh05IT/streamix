package com.varsh.streamix.service;



import com.varsh.streamix.model.UserDetails;
import com.varsh.streamix.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserServiceImpl {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public String addNewUser(final UserDetails userDetails){

        if (userRepository.existsByUserEmailId(userDetails.getUserEmailId())) {
            return "User already exists";
        }

        UserDetails userAdded = userRepository.save(userDetails);

        if (userAdded != null) {
            return "User registered successfully";
        } else {
            return "Failed to register user";
        }
    }
}
