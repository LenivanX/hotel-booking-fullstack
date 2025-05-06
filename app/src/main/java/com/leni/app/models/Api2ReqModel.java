package com.leni.app.models;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Api2ReqModel {
    String location;
    List<String> rooms;
    String from;
    String to;
}
