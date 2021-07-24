package com.example.googledemo.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Comment {
    @Id
    private String key;
    private String username;
    private String comment;
    private String pollKey;
}
