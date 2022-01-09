package org.school.diary.controller;


import lombok.RequiredArgsConstructor;
import org.school.diary.config.UserPrincipal;
import org.school.diary.model.common.User;
import org.school.diary.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final UserService userService;

    @RequestMapping("/")
    public String deafultPage() {
        return "/login";
    }

    //PANEL PO ZALOGOWANIU
    @GetMapping("/home")
    public String firstPage(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        User user = userPrincipal.getUser();
        if(user.getRoles().stream().iterator().next().getName().equals("DIRECTOR")){

            return "redirect:/home/director/rejestracja_ucznia_i_rodzica";
        }else if(user.getRoles().stream().iterator().next().getName().equals("TEACHER")){

            return "redirect:/home/teacher/plan_lekcji";
        }else if(user.getRoles().stream().iterator().next().getName().equals("STUDENT")){

            return "redirect:/home/student/plan_lekcji";
        }else if(user.getRoles().stream().iterator().next().getName().equals("PARENT")){

            return "redirect:/home/parent/plan_lekcji";
        }else{
            return "/home/home_page";
        }
    }

    //PANEL LOGOWANIA
    @GetMapping("/login")
    public String login() {

        return "login";
    }


/*
    //PANEL DO REJESTRACJI
    @GetMapping("/signup")
    public String signUp(Model model){
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
*/


}
