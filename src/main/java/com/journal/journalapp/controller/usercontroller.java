package com.journal.journalapp.controller;

import com.journal.journalapp.entity.user;
import com.journal.journalapp.repository.userrepository;
import com.journal.journalapp.service.userservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class usercontroller {

    @Autowired
    private userservice userservice;

    @Autowired
    private userrepository userrepository;

    @GetMapping
    public ResponseEntity<?> gettheusers(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        if (username != null) {
            user userindb = userservice.findByUserName(username);
            return new ResponseEntity<>(userindb,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public void createuser(@RequestBody user user){
        userservice.savedNewUser(user);
    }

    @PutMapping
        public ResponseEntity<?> updateuser(@RequestBody user user ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        user userindb = userservice.findByUserName(username);
        if (userindb != null) {
            if (user.getUsername() != null && !user.getPassword().trim().isEmpty()){
                userindb.setUsername(user.getUsername());
            }
            if (user.getPassword() != null && !user.getPassword().trim().isEmpty()) {
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                userindb.setPassword(encoder.encode(user.getPassword()));

            }
            userservice.saveEntry(userindb);
            return new ResponseEntity<>(HttpStatus.OK);
        }
         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteuser(@RequestBody user user ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userrepository.deleteByUsername(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }



    }
