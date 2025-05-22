package com.varsh.streamix.controller;

import com.varsh.streamix.model.UserDetails;
import com.varsh.streamix.repository.UserRepository;
import com.varsh.streamix.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private UserRepository userRepository;
    
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/signup")
    public String signupPage(Model model) {
        model.addAttribute("userDetails", new UserDetails());
        return "signup";
    }
    

    @PostMapping("/register")
    public String addNewUser(@ModelAttribute UserDetails userDetails, Model model) {
        String result = userService.addNewUser(userDetails);
        model.addAttribute("message", result);
        return result.equals("User registered successfully") ? "redirect:/stream/home" : "signup"; 
    }
    
    @PostMapping("/login")
    public String login(@ModelAttribute UserDetails userDetails, HttpServletRequest request, Model model) {
        
        String result = userService.login(userDetails);
        System.out.println(result);
        
        HttpSession session = request.getSession(true);
        UserDetails loggedInUser = userRepository.findUserByEmailId(userDetails.getUserEmailId());
        if(loggedInUser == null) {
            model.addAttribute("message", "User not found");
            return "login";
        }
        session.setAttribute("user", userDetails);
        session.setAttribute("role", loggedInUser.getRole());
        
        if ("ADMIN".equals(result)) {
            return "redirect:/admin/"; 
        } else if ("USER".equals(result)){
            return "redirect:/stream/home";
        } else {
            model.addAttribute("message", result);
            return "login";
        }
    }
   
    @GetMapping("/logout")
    String logout(HttpSession httpSession, Model model) {
//        System.out.println("httpSession --" + httpSession);
        if (httpSession != null) {
//            System.out.print("Inside if");
            httpSession.invalidate();
            model.addAttribute("message", "Logged out successfully");
            return "index";
        }
        model.addAttribute("message", "No session found");
        return "login";
    }
    
}
