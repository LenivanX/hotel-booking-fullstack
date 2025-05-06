package com.leni.app.Documents;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Document("hotels")
@Getter
@Setter
@ToString
public class HotelsDocument {
    @Id
    String _id;
    String location;
    String type;
    Integer capacity;
    List<String> booked;
}
