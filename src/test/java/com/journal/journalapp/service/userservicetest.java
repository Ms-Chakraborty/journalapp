package com.journal.journalapp.service;

import com.journal.journalapp.entity.user;
import com.journal.journalapp.repository.userrepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class userservicetest {

    @Autowired
    private userrepository userrepository;

    @Autowired
    private userservice userservice;

    @Test
    public void testfindbyusername(){
        assertNotNull(userrepository.findByUsername("Rahul"));
    }

    @ParameterizedTest
    @CsvSource({
            "Rishi",
            "Rahul"
    })
    public void testjournalentryemptytest(String username){
        user user=userrepository.findByUsername(username);
        assertTrue(!user.getJournalentries().isEmpty());
    }

    @ParameterizedTest
    @ArgumentsSource(userargumentsprovider.class)
    public void testcreateuser(user user){
        assertTrue(userservice.savedNewUser(user));
    }

}
