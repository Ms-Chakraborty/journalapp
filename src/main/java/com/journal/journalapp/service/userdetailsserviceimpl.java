package com.journal.journalapp.service;

import com.journal.journalapp.entity.user;
import com.journal.journalapp.repository.userrepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class userdetailsserviceimpl implements UserDetailsService {
    @Autowired
    private userrepository userrepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        user user=userrepository.findByUsername(username);
        if(user!=null){
            UserDetails userDetails=org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .roles(user.getRoles().toArray(new String[0]))
                    .build();
            return userDetails;

        }
        throw new UsernameNotFoundException("User not found with username"+username);
    }
}
