package org.school.diary.controller;

import org.school.diary.dto.UserDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ParentController {



    //USTAWIENIA
    @GetMapping("/home/parent/ustawienia")
    public String getSettings(Model model) {



       // model.addAttribute("userDTO", new UserDTO());
        return "parent/settings";
    }

}
