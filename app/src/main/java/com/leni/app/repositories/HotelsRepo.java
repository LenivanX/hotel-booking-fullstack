package com.leni.app.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.leni.app.documents.HotelsDocument;

@Repository
public interface HotelsRepo extends MongoRepository<HotelsDocument, String> {
    /**
     * <p>
     * { location : {location}, booked : { $not : { $in : [bookedDatesList] }}}
     * </p>
     * <p>
     * find all hotels which are available for the given dates, for the given
     * location.
     * </p>
     * 
     * @param location        location for booking
     * @param bookedDatesList list of dates for booking
     * @return all documents that match the query
     */
    @Query("{location:?0,booked:{$not:{$in:?1}}}")
    public List<HotelsDocument> finDocumentsByLocationCapacityBookedDates(String location,
            List<String> bookedDatesList);

}
