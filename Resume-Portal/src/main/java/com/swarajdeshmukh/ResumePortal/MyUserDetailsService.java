package com.swarajdeshmukh.resumeportal;

import com.swarajdeshmukh.resumeportal.models.MyUserDetails;
import com.swarajdeshmukh.resumeportal.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

   @Autowired
   UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByuserName(userName);

        user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + userName ));

        return user.map(MyUserDetails::new).get();

        //In a nutshell what this is doing is this is overriding default loadUser functionality
        // of Spring Security and I'm saying im gonna load my user itself

        //Take the userName find it in my userRepository and then i'm gonna convert it into spring security instance which is MyUserDetails
    }
}
