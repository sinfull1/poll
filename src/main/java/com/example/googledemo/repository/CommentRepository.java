package com.example.googledemo.repository;

import com.example.googledemo.model.Bid;
import com.example.googledemo.model.Comment;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;

@Repository
public interface CommentRepository extends ReactiveCrudRepository<Comment, String> {
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    @Query("select key, username, comment,pollkey from comment where pollkey=$1")
    Flux<Comment> findByPollKey(String pollKey);
}
