package com.example.googledemo.model;


import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Bid {
    @Id
    private String key;
    private String username;
    private Double amount;
    private Boolean side;
    private String pollkey;
}
