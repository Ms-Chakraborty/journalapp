package com.journal.journalapp.repository;

import com.journal.journalapp.entity.journalentry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface journalentryrepository extends MongoRepository<journalentry, ObjectId> {

}
