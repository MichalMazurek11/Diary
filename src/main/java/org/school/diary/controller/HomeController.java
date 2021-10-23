package org.school.diary.controller;



import org.school.diary.model.User;
import org.school.diary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@Controller
@RequestMapping
public class HomeController {


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;



    @Autowired
    public HomeController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder =passwordEncoder;


    }


    @GetMapping("/home/hi")
    public String firstPage(Principal prin, Model model) {
        return "/home/hi";
    }

    //PANEL LOGOWANIA
    @GetMapping("/login")
    public String login() {

        return "login";
    }

    @GetMapping({"/home/index", "/"})
    public String index(Principal prin, Model model) {

        return "/home/index";
    }

    @GetMapping("signup")
    public String signUp() {
        return "signup";
    }

    @PostMapping("signup")
    public String signup( @ModelAttribute User user){


//

//        if(userService.findEmail(user.getEmail())==true){
//
//            System.out.println("JEst taki uzytkownik");
//        }
//        if(userService.find(user.getEmail()) == true){
//
//            System.out.println("Istnieje taki uzytkownik");
//        }

//        user.setCreatedDate(LocalDateTime.now());
//        user.setAccountStatus(Status.AKTYWNE);
//        user.setType(Type.USER);
        user.setRole("USER");
        userService.save(user);
        return "redirect:/login";       // przekierowanie na adres metodą GET

    }



}
