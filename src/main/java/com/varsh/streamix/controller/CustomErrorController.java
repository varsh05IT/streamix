package com.varsh.streamix.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomErrorController implements ErrorController {

//    @GetMapping("/error")
//    public String handleError() {
//        return "The link you followed may be broken, or the page may have been removed.";
//    }
//
//    public String getErrorPath() {
//        return "/error";
//    }
}
