package com.swarajdeshmukh.resumeportal;

import com.swarajdeshmukh.resumeportal.models.Education;
import com.swarajdeshmukh.resumeportal.models.Job;
import com.swarajdeshmukh.resumeportal.models.User;
import com.swarajdeshmukh.resumeportal.models.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

        return "index";
//        Optional<UserProfile> profileOptional =  userProfileRepository.findByuserName("einstein");
//        profileOptional.orElseThrow(() -> new RuntimeException("Not found " ));
//
//        UserProfile profile1 = profileOptional.get();
//        Job job1 = new Job();
//        job1.setCompany("Company 1");
//        job1.setDesignation("Designation");
//        job1.setId(1);
//        job1.setStartDate(LocalDate.of(2020,1,1));
//        job1.setEndDate(LocalDate.of(2020,3,1));
//        job1.getResponsibilities().add("Theory of relativity and neuclear bomb");
//        job1.getResponsibilities().add("Also made contrubutions to physics ");
//        job1.getResponsibilities().add("Also made contrubutions to quantum mechanics ");
//
//        job1.setCurrentJob(true);
//
//        Job job2 = new Job();
//        job2.setCompany("Company 2");
//        job2.setDesignation("Designation");
//        job2.setId(2);
//        job2.setStartDate(LocalDate.of(2019,5,1));
//        job2.setEndDate(LocalDate.of(2020,1,1));
//        job2.getResponsibilities().add("Theory of relativity and neuclear bomb");
//        job2.getResponsibilities().add("Also made contrubutions to physics ");
//        job2.getResponsibilities().add("Also made contrubutions to quantum mechanics ");
//
//
////        List<Job> jobs = new ArrayList<Job>();
////        jobs.add(job1);
////        jobs.add(job2);
//        profile1.getJobs().clear();
//        profile1.getJobs().add(job1);
//        profile1.getJobs().add(job2);
//
//        Education e1 = new Education();
//        e1.setCollege("Harvard College");
//        e1.setQualification("Masters");
//        e1.setSummary("Worked a lot on my thesis");
//        e1.setStartDate(LocalDate.of(2019,5,1));
//        e1.setEndDate(LocalDate.of(2020,1,1));
//
//        Education e2 = new Education();
//        e2.setCollege("Harvard College");
//        e2.setQualification("Phd");
//        e2.setSummary("Worked a lot on my thesis");
//        e2.setStartDate(LocalDate.of(2019,5,1));
//        e2.setEndDate(LocalDate.of(2020,1,1));
//
//        profile1.getEducations().clear();
//        profile1.getEducations().add(e1);
//        profile1.getEducations().add(e2);
//        profile1.getSkills().clear();
//        profile1.getSkills().add("Problem Solving");
//        profile1.getSkills().add("Reading");
//        profile1.getSkills().add("Breaking laws of physics");
//
//
//        userProfileRepository.save(profile1);

    }

    @GetMapping("/edit")
    public String edit(Model model, Principal principal, @RequestParam(required = false) String add){
        String userId = principal.getName();
        model.addAttribute("userId", userId);
        Optional<UserProfile> userProfileOptional = userProfileRepository.findByuserName(userId); //fetch object from database pass the userId
        userProfileOptional.orElseThrow(() -> new RuntimeException("Not found: " + userId));
        UserProfile userProfile = userProfileOptional.get(); //get the user profile and set to model attribute
        if ("job".equals(add)) {
            userProfile.getJobs().add(new Job());
        } else if ("education".equals(add)) {
            userProfile.getEducations().add(new Education());
        } else if ("skill".equals(add)) {
            userProfile.getSkills().add("");
        }

        model.addAttribute("userProfile", userProfile);
        return "profile-edit";
    }

    @GetMapping("/delete")
    public String delete(Model model, Principal principal, @RequestParam String type, @RequestParam int index) {
        String userId = principal.getName();
        Optional<UserProfile> userProfileOptional = userProfileRepository.findByuserName(userId);
        userProfileOptional.orElseThrow(() -> new RuntimeException("Not found: " + userId));
        UserProfile userProfile = userProfileOptional.get();
        if ("job".equals(type)) {
            userProfile.getJobs().remove(index);
        } else if ("education".equals(type)) {
            userProfile.getEducations().remove(index);
        } else if ("skill".equals(type)) {
            userProfile.getSkills().remove(index);
        }
        userProfileRepository.save(userProfile);
        return "redirect:/edit";

    }


    @PostMapping("/edit") //post request to same URL
    public String postEdit(Model model, Principal principal, @ModelAttribute UserProfile userProfile){ //the way to get hold of object is ModelAttribute object userprofile is gonna contain all the value user entered
        String userName = principal.getName();
        //the user profile that you gonna get here is not gonna contain the ID of this object
        //so we have to set the ID that wa spersisted for this object
        //This is how we get the User ID
        Optional<UserProfile> userProfileOptional = userProfileRepository.findByuserName(userName); //fetch object from database pass the userId
        userProfileOptional.orElseThrow(() -> new RuntimeException("Not found: " + userName));
        UserProfile savedUserProfile = userProfileOptional.get();
        userProfile.setId(savedUserProfile.getId());
        userProfile.setUserName(userName);
        userProfileRepository.save(userProfile);
        //model.addAttribute("userId", principal.getName());
        return "redirect:/view/" + userName;  //redirect to user profile
    }

    @GetMapping("/view/{userId}")
    public String view(Principal principal, @PathVariable String userId, Model model)
    //Model is spring MVC class which allows me to putsomething on return type
    {

        //When i'm doing a repository call what i'm getting is a optional ok it
        //may or may not exists so i'm gonna do a .orElseThrow if value does not exist runtimeException
        //If value does exist im gonna use actual userprofile and that what i'm gonna use to get model attribute and return
        if (principal != null && principal.getName() != "") {
            boolean currentUsersProfile = principal.getName().equals(userId);
            model.addAttribute("currentUsersProfile", currentUsersProfile);
        }
        String userName = principal.getName();
        Optional<UserProfile> userProfileOptional = userProfileRepository.findByuserName(userId);
        userProfileOptional.orElseThrow(() -> new RuntimeException("Not found: " + userId ));
        

        model.addAttribute("userId", userId);
        UserProfile userProfile = userProfileOptional.get();
        model.addAttribute("userProfile", userProfile);

        System.out.println(userProfile.getJobs());

        return "profile-templates/" + userProfile.getTheme() + "/index";
    }
}
