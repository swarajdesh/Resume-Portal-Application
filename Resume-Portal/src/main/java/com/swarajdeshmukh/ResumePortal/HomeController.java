package com.swarajdeshmukh.resumeportal;

import com.swarajdeshmukh.resumeportal.models.Job;
import com.swarajdeshmukh.resumeportal.models.User;
import com.swarajdeshmukh.resumeportal.models.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

/**@author : Swaraj Deshmukh
 *  Date : 22/01/2021
 *
 */

@Controller //need to make this controller so that it know it is a spring MVC controller
public class HomeController {

    @Autowired
    UserProfileRepository userProfileRepository;

    @GetMapping("/")
    public String home(){
        UserProfile profile1 = new UserProfile();
        profile1.setId(1);
        profile1.setDesignation("Designation");
        profile1.setUserName("einstein");
        profile1.setFirstname("Albert");
        profile1.setLastname("Einstein");
        profile1.setTheme(1);

        Job job1 = new Job();
        job1.setCompany("Company 1");
        job1.setDesignation("Designation");
        job1.setId(1);
        job1.setStartDate(LocalDate.of(2020,1,1));
        job1.setEndDate(LocalDate.of(2020,3,1));

        Job job2 = new Job();
        job2.setCompany("Company 2");
        job2.setDesignation("Designation");
        job2.setId(2);
        job2.setStartDate(LocalDate.of(2019,5,1));
        job2.setEndDate(LocalDate.of(2020,1,1));
        profile1.setJobs(Arrays.asList(job1, job2));

        userProfileRepository.save(profile1);

        return "profile";
    }

    @GetMapping("/edit")
    public String edit(){
        return "edit page";
    }

    @GetMapping("/view/{userId}")
    public String view(@PathVariable String userId, Model model)
    //Model is spring MVC class which allows me to putsomething on return type
    {

        //When i'm doing a repository call what i'm getting is a optional ok it
        //may or may not exists so i'm gonna do a .orElseThrow if value does not exist runtimeException
        //If value does exist im gonna use actual userprofile and that what i'm gonna use to get model attribute and return
        Optional<UserProfile> userProfileOptional = userProfileRepository.findByuserName(userId);
        userProfileOptional.orElseThrow(() -> new RuntimeException("Not found: " + userId ));
        

        model.addAttribute("userId", userId);
        UserProfile userProfile = userProfileOptional.get();
        model.addAttribute("userProfile", userProfile);

        System.out.println(userProfile.getJobs());

        return "profile-templates/" + userProfile.getTheme() + "/index";
    }
}
