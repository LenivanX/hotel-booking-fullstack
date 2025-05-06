package com.leni.app.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.leni.app.models.Api1RespSubModel;
import com.leni.app.services.WebServices;
import com.leni.app.utilities.CommonUtils;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class WebController {
    @Autowired
    WebServices webServices;

    @GetMapping("api1/find-avail-rooms")
    public Map<String, Map<String, Api1RespSubModel>> getAvailableRooms(@RequestParam String location,
            @RequestParam String from, @RequestParam String to) {
        return webServices.findAvailableRooms(location, CommonUtils.getBookedDates(from, to));
    }

}
