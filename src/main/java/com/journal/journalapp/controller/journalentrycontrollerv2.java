package com.journal.journalapp.controller;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import com.journal.journalapp.entity.journalentry;
import com.journal.journalapp.entity.user;
import com.journal.journalapp.service.journalentryservice;
import com.journal.journalapp.service.userservice;
import org.apache.catalina.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/journal")
public class journalentrycontrollerv2 {

    @Autowired
    private journalentryservice journalentryservice;
    @Autowired
    private userservice userservice;

    @GetMapping
    public ResponseEntity<List<journalentry>> getAllJournalEntryByUser(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
        user user=userservice.findByUserName(username);
        List<journalentry> all=user.getJournalentries();
        if(all!=null&& !all.isEmpty()){
            return new ResponseEntity<>(all,HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping
    public ResponseEntity<journalentry> createEntry(@RequestBody journalentry myentry){
        try {
            Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
            String username=authentication.getName();
                journalentryservice.saveEntry(myentry,username);
                return new ResponseEntity<>(myentry, HttpStatus.CREATED);

        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("id/{id}")
    public ResponseEntity<journalentry>  getjournalentrybyid(@PathVariable ObjectId id){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
        user user= userservice.findByUserName(username);
        List<journalentry> card=user.getJournalentries().stream()
                .filter(x->x.getId().equals(id))
                .collect(Collectors.toList());
        if(!card.isEmpty()){
            journalentry journal= card.get(0);//card will always only have one element as all id s are unique
            if(journal!=null){
                return new ResponseEntity<>(journal, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>( HttpStatus.NOT_FOUND);
    }
    @DeleteMapping ("id/{id}")
    public ResponseEntity<?> deletejournalentrybyid(@PathVariable ObjectId id){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            user user = userservice.findByUserName(username);
            if(user!=null) {
                journalentryservice.deletebyid(id, username);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else{
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
    @PutMapping("id/{id}")
    public ResponseEntity<?> updatejournalentrybyid(@PathVariable ObjectId id,
                                                    @RequestBody journalentry newentry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        user user = userservice.findByUserName(username);
        if (user != null) {
            journalentry oldentry = journalentryservice.findbyid(id).orElse(null);
            if (oldentry != null) {
                oldentry.setTitle(newentry.getTitle() != null && !newentry.getTitle().equals("") ? newentry.getTitle() : oldentry.getTitle());
                oldentry.setContent(newentry.getContent() != null && !newentry.getContent().equals("") ? newentry.getContent() : oldentry.getContent());
                journalentryservice.saveEntry(oldentry);
                return new ResponseEntity<journalentry>(oldentry, HttpStatus.ACCEPTED);

            }
        }

            return new ResponseEntity<journalentry>(HttpStatus.NOT_FOUND);

    }

}
