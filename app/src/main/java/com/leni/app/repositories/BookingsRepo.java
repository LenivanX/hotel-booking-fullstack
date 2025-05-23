package com.leni.app.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.leni.app.documents.BookingsDocument;

@Repository
public interface BookingsRepo extends MongoRepository<BookingsDocument, String> {

}
