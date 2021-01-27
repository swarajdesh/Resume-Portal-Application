package com.swarajdeshmukh.resumeportal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**@author : Swaraj Deshmukh
 *  Date : 22/01/2021
 *
 */

@Controller //need to make this controller so that it know it is a spring MVC controller
public class HomeController {

    @GetMapping("/")
    public String home(){
        return "hello";
    }

    @GetMapping("/edit")
    public String edit(){
        return "edit page";
    }

    @GetMapping("/view/{userId}")
    public String view(@PathVariable String userId, Model model)
    //Model is spring MVC class which allows me to putsomething on return type
    {
        model.addAttribute("userId", userId);
        return "profile";
    }
}
