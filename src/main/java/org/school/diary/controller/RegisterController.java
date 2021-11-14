package org.school.diary.controller;



import lombok.RequiredArgsConstructor;
import org.school.diary.dto.UserDTO;
import org.school.diary.mappers.SignedUserMapper;
import org.school.diary.model.common.User;
import org.school.diary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;


@Controller
@RequestMapping
@RequiredArgsConstructor
@Validated
public class RegisterController {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;



    //PANEL PO ZALOGOWANIU
    @GetMapping("/home")
    public String firstPage(Principal prin, Model model) {

        return "/home/home_page";
    }

    //PANEL LOGOWANIA
    @GetMapping("/login")
    public String login() {

        return "login";
    }


//    @GetMapping({"/home/index"})
//    public String index(Principal prin, Model model) {
//
//        return "/home/index";
//    }

    //PANEL DO REJESTRACJI
    @GetMapping("signup")
    public String signUp(Model model) {
       model.addAttribute("userDto", new UserDTO());
        return "signup";
    }

    //PRZYCISK REJESTRACJI
    @PostMapping("signup")
    public String signup(@Valid UserDTO userDTO, BindingResult result){
        userService.saveNewUser(userDTO);
        return "redirect:/login";       // przekierowanie na adres metodą GET

    }



}
