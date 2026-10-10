package com.journal.journalapp.service;

import com.journal.journalapp.entity.user;
import com.journal.journalapp.repository.userrepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class userservice {
    @Autowired
    private userrepository userrepository;

    private static final PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();

    public void saveEntry(user user){
       userrepository.save(user);
    }

    public boolean savedNewUser(user user){
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("User"));
            userrepository.save(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void savedNewAdmin(user user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("admin"));
        userrepository.save(user);
    }

    public List<user> getAll(){
        return userrepository.findAll();
    }

    public Optional<user> findbyid(ObjectId id){
        return userrepository.findById(id);
    }

    public void deletebyid(ObjectId id){
        userrepository.deleteById(id);
    }

    public user findByUserName(String userName){
        return userrepository.findByUsername(userName);
    }
}
