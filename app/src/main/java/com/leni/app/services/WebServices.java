package com.leni.app.services;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leni.app.documents.BookingsDocument;
import com.leni.app.documents.HotelsDocument;
import com.leni.app.models.Api1RespSubModel;
import com.leni.app.models.Api2ReqModel;
import com.leni.app.models.Api2RespModel;
import com.leni.app.repositories.BookingsRepo;
import com.leni.app.repositories.HotelsRepo;
import com.leni.app.utilities.CommonUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class WebServices {
    @Autowired
    HotelsRepo hotelsRepo;
    @Autowired
    BookingsRepo bookingsRepo;

    /**
     * Find all available rooms for the given location and the booking dates
     * 
     * @param location        location for booking
     * @param bookedDatesList list of dates for booking
     * @return all rooms that are available for the given dates and location
     */
    public Map<String, Map<String, Api1RespSubModel>> findAvailableRooms(String location,
            List<String> bookedDatesList) {
        Map<String, Api1RespSubModel> subResponse = new HashMap<>();
        Map<String, Map<String, Api1RespSubModel>> response = new HashMap<>();
        List<HotelsDocument> allAvailableRooms = hotelsRepo.finDocumentsByLocationCapacityBookedDates(location,
                bookedDatesList);
        for (HotelsDocument doc : allAvailableRooms) {
            if (subResponse.containsKey(doc.getType())) {
                Api1RespSubModel temp = subResponse.get(doc.getType());
                temp.getRooms().add(doc.get_id());
            } else {
                subResponse.put(doc.getType(),
                        new Api1RespSubModel(doc.getCapacity(), new ArrayList<>(Arrays.asList(doc.get_id()))));
            }
        }
        response.put(location, subResponse);
        return response;
    }

    /**
     * book rooms for a provided list of dates
     * 
     * @param req request payload
     * @return
     * @throws Exception
     */
    public Api2RespModel bookRooms(Api2ReqModel req) throws Exception {
        BookingsDocument doc = new BookingsDocument();
        doc.set_id(UUID.randomUUID().toString());
        doc.setFrom(req.getFrom());
        doc.setTo(req.getTo());
        doc.setRooms(req.getRooms());
        doc.setBookedAt(Timestamp.from(Instant.now()).toString());

        List<String> bookedDateList = CommonUtils.getBookedDates(req.getFrom(), req.getTo());
        for (String room : req.getRooms()) {
            Optional<HotelsDocument> optionalHotel = hotelsRepo.findById(room);
            if (optionalHotel.isPresent()) {
                HotelsDocument hotel = optionalHotel.get();
                if (!hotel.getBooked().containsAll(bookedDateList)) {
                    hotel.getBooked().addAll(bookedDateList);
                    hotelsRepo.save(hotel);
                } else {
                    throw new Exception("date(s) already booked for " + room);
                }
            }
        }
        return new Api2RespModel(bookingsRepo.save(doc).get_id());
    }
}
