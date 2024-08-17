package com.scm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.scm.entities.Contact;



@Controller
@RequestMapping("/user")
public class UserController {


    // Endpoints for user
    // dashboard endpoint
    @RequestMapping("/dashboard")
    // Map to /user/dashboard endpoint and return user dashboard page.
    public String userDashboard(){

        return "user/dashboard";
    }
    // // add contact endpoint
    // @RequestMapping(value="/addcontact", method=RequestMethod.GET)
    // public String requestMethodName() {
    //     return "user/addcontact";
    // }
    
    // update contact endpoint
    // delete contact endpoint
    // view contact endpoint
    // user profile endpoint




}