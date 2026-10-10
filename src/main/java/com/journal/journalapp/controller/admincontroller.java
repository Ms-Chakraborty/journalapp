package com.journal.journalapp.controller;

import com.journal.journalapp.entity.user;
import com.journal.journalapp.repository.userrepository;
import com.journal.journalapp.service.userservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class admincontroller {
    @Autowired
    private userservice userservice;

    @Autowired
    private userrepository userrepository;
    @GetMapping("/all-users")
    public ResponseEntity<?> getallusers(){
        List<user> all=userservice.getAll();
        if(all!=null &&!all.isEmpty()){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND) ;
    }
    @PostMapping("/create-admin")
    public void createadmin(@RequestBody user user){
        userservice.savedNewAdmin(user);
    }
}
