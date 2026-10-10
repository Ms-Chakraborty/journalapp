package com.journal.journalapp.service;

import com.journal.journalapp.entity.user;
import com.journal.journalapp.repository.userrepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Arrays;

import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
public class userdetailsserviceimpltest {


    @Mock
    private userrepository userrepository;

    @InjectMocks
    private userdetailsserviceimpl userdetailsserviceimpl;

    @Test
    void loadusernametest(){
    when(userrepository.findByUsername(ArgumentMatchers.anyString()))
            .thenReturn(user.builder().username("Ravi").password("Ravi").roles(Arrays.asList("User")).build());
    UserDetails user= userdetailsserviceimpl.loadUserByUsername("Ravi");
    }
}
