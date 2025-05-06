package com.leni.app.documents;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Document("bookings")
@Getter
@Setter
@ToString
public class BookingsDocument {
    @Id
    String _id;
    List<String> rooms;
    String from;
    String to;
    String bookedAt;
}
