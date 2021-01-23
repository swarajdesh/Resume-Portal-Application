package com.swarajdeshmukh.resumeportal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**@author : Swaraj Deshmukh
 *  Date : 22/01/2021
 *
 */

@RestController
public class HomeController {

    @GetMapping("/")
    public String home(){
        return "hello";
    }

    @GetMapping("/edit")
    public String edit(){
        return "edit page";
    }
}
