package com.leni.app.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class Api1RespSubModel {
    Integer capacity;
    List<String> rooms;
}
