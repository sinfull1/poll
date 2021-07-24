package com.example.googledemo.model;


import lombok.Data;

import java.util.List;

@Data
public class PollsReturn {
    private Polls polls;
    private List<Bid> bids;
    private List<Comment> comments;
}
