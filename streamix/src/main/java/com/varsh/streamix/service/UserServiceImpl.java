package com.varsh.streamix.service;

import com.varsh.streamix.dao.UserDAO;
import com.varsh.streamix.model.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl {

    @Autowired
    private UserDAO userDAO;

    @Transactional
    public String addNewUser(final UserDetails userDetails){

        if (userDAO.checkUserExists(userDetails.getUserEmailId())) {
            return "User already exists";
        }

        boolean userAdded = userDAO.addNewUser(userDetails);

        if (userAdded) {
            return "User registered successfully";
        } else {
            return "Failed to register user";
        }
    }
}
