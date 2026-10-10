package com.journal.journalapp.repository;

import com.journal.journalapp.entity.user;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface userrepository extends MongoRepository<user, ObjectId> {
    user findByUsername(String username);
    void deleteByUsername(String username);
}
