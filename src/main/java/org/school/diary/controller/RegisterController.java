package org.school.diary.controller;



import lombok.RequiredArgsConstructor;
import org.school.diary.dto.UserDTO;
import org.school.diary.mappers.SignedUserMapper;
import org.school.diary.model.common.PersonRelatedWithSchool;
import org.school.diary.model.common.Student;
import org.school.diary.model.common.User;
import org.school.diary.service.RoleService;
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
import java.util.ArrayList;
import java.util.List;


@Controller
@RequiredArgsConstructor
public class RegisterController {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @Autowired
    RoleService roleService;


    @RequestMapping("/")
    public String deafultPage()   {
        return "/login";
    }

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


    //PANEL DO REJESTRACJI
    @GetMapping("/signup")
    public String signUp(Model model) {

        List<String> listOfRoles = new ArrayList<>();
        listOfRoles.add("student");
        listOfRoles.add("parent");
        listOfRoles.add("teacher");
        listOfRoles.add("director");


        model.addAttribute("listOfRoles", roleService.listOfRoles());
        model.addAttribute("userDTO", new UserDTO());
        return "signup";
    }

    //PRZYCISK REJESTRACJI
    @PostMapping("signup")
    public String signup(@Valid @ModelAttribute("userDTO") UserDTO userDTO, BindingResult bindingResult, Model model){

        List<String> listOfRoles = new ArrayList<>();

        listOfRoles.add("student");
        listOfRoles.add("parent");
        listOfRoles.add("teacher");
        listOfRoles.add("director");

        if(bindingResult.hasErrors()){
            model.addAttribute("listOfRoles",roleService.listOfRoles() );
            return "signup";
        }else{
            userService.saveNewUser(userDTO);
        }
        model.addAttribute("userDTO", new UserDTO());
        model.addAttribute("listOfRoles", listOfRoles);
        return "redirect:/login";       // przekierowanie na adres metodą GET
    }



}
