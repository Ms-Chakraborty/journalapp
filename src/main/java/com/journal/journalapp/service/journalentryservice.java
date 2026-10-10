package com.journal.journalapp.service;

import com.journal.journalapp.entity.journalentry;
import com.journal.journalapp.entity.user;
import com.journal.journalapp.repository.journalentryrepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.bson.types.ObjectId;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;




@Component
@Slf4j
public class journalentryservice {
    @Autowired
    private journalentryrepository journalentryrepository;
    @Autowired
    private userservice userservice;


    @Transactional
    public void saveEntry(journalentry journalentry, String username){
        try {
            user user=userservice.findByUserName(username);
            journalentry.setDate(LocalDateTime.now());
            journalentry savedentry=journalentryrepository.save(journalentry);
            user.getJournalentries().add(savedentry);
            userservice.saveEntry(user);
            log.info("Successfully saved to mongodb with ID:" + savedentry.getId());
        }catch(Exception e){
            user user=userservice.findByUserName(username);
            log.error("Error occured for {}",user.getUsername(),e);
        }
    }

    @Transactional
    public void saveEntry(journalentry journalentry){
        try {
            journalentry.setDate(LocalDateTime.now());
            journalentry savedentry=journalentryrepository.save(journalentry);
            System.out.println ("Successfully saved to mongodb with ID:" + savedentry.getId());
        }catch(Exception e){
            log.error("Exception",e);
        }
    }

    public List<journalentry> getAll(){
        return journalentryrepository.findAll();
    }

    public Optional<journalentry> findbyid(ObjectId id){
        return journalentryrepository.findById(id);
    }

    @Transactional
    public void deletebyid(ObjectId id,String username){
        try {
            user user = userservice.findByUserName(username);
            user.getJournalentries().removeIf(x -> x.getId().equals(id));
            userservice.saveEntry(user);//It simply updates the user, doesn't create new user if it already exists
            System.out.println("Successfully deleted from mongodb with ID:" + id);
            journalentryrepository.deleteById(id);
        } catch (Exception e) {
            log.error("Error",e);
            throw new RuntimeException("An error occured while deleing entry",e);
        }

    }
}
