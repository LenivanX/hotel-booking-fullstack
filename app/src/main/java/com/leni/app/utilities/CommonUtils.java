package com.leni.app.utilities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CommonUtils {
    /**
     * find the list of booking dates using FROM and TO dates.
     * 
     * @param from check-in date
     * @param to   check-out date
     * @return list of booking dates
     */
    public List<String> getBookedDates(String from, String to) {
        List<String> bookDatesList = new ArrayList<>();
        LocalDate fromDate = LocalDate.parse(from);
        LocalDate toDate = LocalDate.parse(to);
        while (fromDate.isBefore(toDate)) {
            bookDatesList.add(fromDate.toString());
            fromDate = fromDate.plusDays(1);
        }
        return bookDatesList;
    }

    /**
     * to verify that FROM date is at least 1 day before TO date
     * 
     * @param from check-in date
     * @param to   check-out date
     * @return true/false
     */
    public boolean validateDates(String from, String to) {
        if (LocalDate.parse(from).isBefore(LocalDate.parse(to))) {
            return true;
        } else {
            return false;
        }
    }
}
