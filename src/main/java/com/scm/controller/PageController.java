package com.scm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.scm.entities.User;
import com.scm.form.UserForm;
import com.scm.helper.Message;
import com.scm.helper.MessageType;
import com.scm.service.UserService;
import com.scm.service.implimentation.UserServiceImplimentation;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class PageController {

    @Autowired
    private UserService userService;

    // Home page redirection when user does not provide any endpoints and not logged in
    @RequestMapping("/")
public String index(){
        return "redirect:/home";
    }
// Homepage or
    @RequestMapping("/home")
    public String home() {
        // home is a template(html file) which is in resources/templates/
        return "home";
    }

    @RequestMapping("/about")
    public String about(Model model) {
        // Model model use to send data to template html pages
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

    // Use to login user it diplayed the login form and other login options.
    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    // Use to Register user it diplayed the Registration form and other registration options.
    @RequestMapping("/register")
    public String register(Model model) {
        // We have a class UserForm which has fields that we need for registration
        UserForm userForm = new UserForm();
        // we are sending a empty object of this class which will displayed in the form that is nothing.
        model.addAttribute("userForm", userForm);
        // But we also get the values in this object when user make changes in the form because they both are same.
        return "register";
    }

    // When user fill registration form and submit, it is submitted to this url and it manage registration process.
    @PostMapping("/do-register")
    public String registerUseString(@Valid @ModelAttribute UserForm userForm,BindingResult bindingResult, HttpSession session) { //@Valid check validation and BindingResult get error message if has any.
        // System.out.println("----do-register endpoint------");
        // 1.Fetch data : We get data in userForm object
        System.out.println(userForm);
        // 2.Validate Form data : here we check if error happened then return to register page.
         if(bindingResult.hasErrors()){
             System.out.println("----------------Error----------------");
             return "register";
         }
        // 3.Save to Database
        // User user = User.builder()
        // .name(userForm.getName())
        // .email(userForm.getEmail())
        // // Here we user phonenumber because in the User Model we use phoneNumber instead of contact 
        // .phoneNumber(userForm.getContact())
        // .password(userForm.getPassword())
        // .about(userForm.getAbout())
        // .build();
        
        // When we use builder() the default values(given in Model) are not initialized.
        // Show we use new keyword to create object and then initialize them.
        User user=new User();
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setPhoneNumber(userForm.getContact());
        user.setPassword(userForm.getPassword());
        user.setAbout(userForm.getAbout());

        userService.saveUser(user);
        // 4.Message:for successfull registration in frontend where we using Thymeleaf and Tailwind.
        // we have created a class which define msg,type
        Message message=Message.builder()
        .content("Successfull")
// in which color message should print.(green,red,blue)
        .type(MessageType.green)
        .build();
        session.setAttribute("message", message); 
        // redirect login page
        
        // it will redirect you to /register
        return "redirect:/register";
    }


    @RequestMapping("/user")
    public String userIndex(){
        return "redirect:/user/dashboard";
    }


}
