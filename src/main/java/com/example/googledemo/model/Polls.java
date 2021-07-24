package com.example.googledemo.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Polls {
    @Id
    private String key;
    private String name;
    private String  description;
    private String image;

}
