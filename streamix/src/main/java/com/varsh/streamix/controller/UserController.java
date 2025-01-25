package com.varsh.streamix.controller;

import com.varsh.streamix.model.UserDetails;
import com.varsh.streamix.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController{

    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/register")
    public ResponseEntity<String> addNewUser(@RequestBody UserDetails userDetails) {
        String result = userService.addNewUser(userDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}
