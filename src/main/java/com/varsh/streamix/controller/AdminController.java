package com.varsh.streamix.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.varsh.streamix.model.Role;
import com.varsh.streamix.model.StreamType;
import com.varsh.streamix.model.UserDetails;

import com.varsh.streamix.model.VideoStream;

import com.varsh.streamix.service.UserService;

import com.varsh.streamix.service.VideoStreamService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller

@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private VideoStreamService videoStreamService;

    @GetMapping("/")
    public String adminDashboard() {
        return "dashboard";
    }

    @GetMapping("/manageUsers")
    public String manageUsers(Model model , HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        if(session.getAttribute("role") == Role.ADMIN) {
            List<UserDetails> users = userService.getAllUsers();
            model.addAttribute("users", users);
            return "manageUsers";
        } 
        return "redirect:/stream/unauthorized";
    }

    @GetMapping("/edit-user/{id}")
    public String editUser(@PathVariable Long id, Model model) {
        UserDetails user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "editUser";

    }
    
    @PostMapping("/update-user")
    @ResponseBody
    public Map<String, Object> updateUser(@ModelAttribute UserDetails userDetails) {
        Map<String, Object> response = new HashMap<>();
        try {
            userService.updateUser(userDetails);
            response.put("success", true);
        } catch (Exception e) {
            response.put("success", false);
        }
        return response;
    }

    @GetMapping("/delete-user/{id}")
    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        if(session.getAttribute("role") == Role.ADMIN) {
            userService.deleteUser(id);
            redirectAttributes.addFlashAttribute("message", "User deleted successfully!");
            return "redirect:/admin/manageUsers";
        }
        return "redirect:/stream/unauthorized";
    }

    @GetMapping("/manageStreams")
    public String manageStreams(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        if(session.getAttribute("role") == Role.ADMIN) {
            List<VideoStream> videoStreams = videoStreamService.getAllVideoStreams();
            model.addAttribute("videoStreams", videoStreams);
            return "manageStreams";
        } 
        return "redirect:/stream/unauthorized";
    }

    @GetMapping("/edit-stream/{id}")
    public String editStream(@PathVariable Long id, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        if(session.getAttribute("role") == Role.ADMIN) {
            VideoStream stream = videoStreamService.getVideoById(id);
            model.addAttribute("stream", stream);
            return "editStream";
        }
        return "redirect:/stream/unauthorized";
    }

    @PostMapping("/update-stream")
    public String updateStream(@ModelAttribute VideoStream stream, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        if(session.getAttribute("role") == Role.ADMIN) {
            videoStreamService.updateVideo(stream);
            redirectAttributes.addFlashAttribute("message", "Stream updated successfully!");
            return "redirect:/admin/manageStreams";
        }
        return "redirect:/stream/unauthorized";

    }

    @GetMapping("/delete-stream/{id}")
    public String deleteStream(@PathVariable Long id, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        if(session.getAttribute("role") == Role.ADMIN) {
            boolean isDeleted = videoStreamService.deleteVideo(id);
            if (isDeleted) {
                redirectAttributes.addFlashAttribute("message", "Stream deleted successfully!");
            } else {
                redirectAttributes.addFlashAttribute("message", "Stream not found!");
            }
            return "redirect:/admin/manageStreams";
        }
        return "redirect:/stream/unauthorized";
        
    }

}