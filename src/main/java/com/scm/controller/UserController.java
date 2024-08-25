package com.scm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.scm.entities.Contact;



@Controller
@RequestMapping("/user")
public class UserController {


//    Mapping for user/ url.
    @RequestMapping("/")
    public String index(){
        return "redirect:/user/dashboard";
    }

    // Endpoints for user
    // dashboard endpoint
    @RequestMapping("/dashboard")
    // Map to /user/dashboard endpoint and return user dashboard page.
    public String userDashboard(){
        return "user/dashboard";
    }
     // add contact endpoint
     @RequestMapping(value="/contact", method=RequestMethod.GET)
     public String userContact() {
         return "user/contact";
     }
      // add contact endpoint
      @RequestMapping(value="/profile", method=RequestMethod.GET)
      public String userProfile() {
          return "user/profile";
      }
    
    // update contact endpoint
    // delete contact endpoint
    // view contact endpoint
    // user profile endpoint




}