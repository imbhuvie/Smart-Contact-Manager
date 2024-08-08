package com.scm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class PageController {

  

    @RequestMapping("/home")
    public String home(Model model){
        // model use to send data to template html pages
        model.addAttribute("page", "Home Page");
        model.addAttribute("name", "Bhupendra verma");
        model.addAttribute("github", "https://github.com/imbhuvie");

        //home is a template(html file) which is in resources/templates/
        return "home";
    }
    @RequestMapping("/about")
    public String about(Model model){
        model.addAttribute("name","Bhupendra Verma");
        model.addAttribute("role", "Full Stack Developer");
        model.addAttribute("email", "bhupendra@gmail.com");
        model.addAttribute("linkedinGithub", "imbhuvie");

        return "about";
    }

    @RequestMapping("/service")
    public String service(){
        return "service";
    }
}
