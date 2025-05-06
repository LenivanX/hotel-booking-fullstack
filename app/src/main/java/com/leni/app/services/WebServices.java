package com.leni.app.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leni.app.Documents.HotelsDocument;
import com.leni.app.models.Api1RespSubModel;
import com.leni.app.repositories.HotelsRepo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class WebServices {
    @Autowired
    HotelsRepo hotelsRepo;

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
}
