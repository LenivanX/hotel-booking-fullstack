package com.leni.app.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.leni.app.models.Api2ReqModel;
import com.leni.app.models.ErrorModel;
import com.leni.app.services.WebServices;
import com.leni.app.utilities.CommonUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class WebController {
    @Autowired
    WebServices webServices;

    @GetMapping("api1/find-avail-rooms")
    public ResponseEntity<?> getAvailableRooms(@RequestParam String location,
            @RequestParam String from, @RequestParam String to) {
        try {
            if (!CommonUtils.validateDates(from, to)) {
                throw new Exception("FROM date should be at least 1 day before TO date");
            }
            return new ResponseEntity<>(webServices.findAvailableRooms(location, CommonUtils.getBookedDates(from, to)),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorModel("500", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("api2/book-rooms")
    public ResponseEntity<?> postBookRooms(@RequestBody Api2ReqModel req) {
        try {
            if (!CommonUtils.validateDates(req.getFrom(), req.getTo())) {
                throw new Exception("FROM date should be at least 1 day before TO date");
            }
            return new ResponseEntity<>(webServices.bookRooms(req), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorModel("500", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
