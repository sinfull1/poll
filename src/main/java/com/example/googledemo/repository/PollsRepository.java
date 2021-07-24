package com.example.googledemo.repository;

import com.example.googledemo.model.Polls;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PollsRepository extends ReactiveCrudRepository<Polls,String> {
}
