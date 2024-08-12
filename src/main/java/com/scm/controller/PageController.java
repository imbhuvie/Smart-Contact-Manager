package com.scm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.scm.entities.User;
import com.scm.form.UserForm;
import com.scm.service.UserService;
import com.scm.service.implimentation.UserServiceImplimentation;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class PageController {

    @Autowired
    private UserService userService;

    @RequestMapping("/home")
    public String home(Model model) {
        // model use to send data to template html pages
        model.addAttribute("page", "Home Page");
        model.addAttribute("name", "Bhupendra verma");
        model.addAttribute("github", "https://github.com/imbhuvie");

        // home is a template(html file) which is in resources/templates/
        return "home";
    }

    @RequestMapping("/about")
    public String about(Model model) {
        model.addAttribute("name", "Bhupendra Verma");
        model.addAttribute("role", "Full Stack Developer");
        model.addAttribute("email", "bhupendra@gmail.com");
        model.addAttribute("linkedinGithub", "imbhuvie");

        return "about";
    }

    @RequestMapping("/services")
    public String services() {
        return "services";
    }

    @RequestMapping("/contact")
    public String contact() {
        return "contact";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/register")
    public String register(Model model) {
        UserForm userForm = new UserForm();
        userForm.setName("Bhupendra");
        userForm.setContact("8953738383");
        model.addAttribute("userForm", userForm);
        return "register";
    }

    @PostMapping("/do-register")
    public String registerUseString(@ModelAttribute UserForm userForm) {
        // 1.Fetch data
        System.out.println(userForm);
        // 2.Validate Form data :TODO
        // 3.Save to Database
        User user = User.builder()
        .name(userForm.getName())
        .email(userForm.getEmail())
        // Here we user phonenumber because in the User Model we use phoneNumber instead of contact 
        .phoneNumber(userForm.getContact())
        .password(userForm.getPassword())
        .about(userForm.getAbout())
        .build();
        userService.saveUser(user);
        // 4.Message:for successfull registration
        // redirect login page

        // it will redirect you to /register
        return "redirect:/register";
    }

}
