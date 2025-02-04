package com.varsh.streamix.controller;

import com.varsh.streamix.model.UserDetails;
import com.varsh.streamix.service.UserServiceImpl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController{

    @Autowired
    private UserServiceImpl userService;
    
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/signup")
    public String signupPage() {
        return "signup";
    }

    @PostMapping("/register")
    public String addNewUser(@ModelAttribute UserDetails userDetails, Model model) {
        String result = userService.addNewUser(userDetails);
        model.addAttribute("message", result);
        return result.equals("User registered successfully") ? "redirect:/user/login" : "signup"; 
    }
    
    @PostMapping("/login")
    public String login(@ModelAttribute UserDetails userDetails, HttpServletRequest request, Model model) {
        String result = userService.login(userDetails);
        if (result.equals("Login successful")) {
            HttpSession session = request.getSession(true);
            session.setAttribute("user", userDetails);
            return "redirect:/video"; 
        } else {
            model.addAttribute("message", result);
            return "login";
        }
    }
    
    @GetMapping("/logout")
    String logout(HttpSession httpSession, Model model) {
        if (httpSession != null) {
            httpSession.invalidate();
            model.addAttribute("message", "Logged out successfully");
            return "login";
        }
        model.addAttribute("message", "No session found");
        return "login";
    }
    
}
