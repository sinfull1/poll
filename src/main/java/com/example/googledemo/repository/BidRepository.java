package com.example.googledemo.repository;

import com.example.googledemo.model.Bid;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface BidRepository extends ReactiveCrudRepository<Bid,String> {


    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    @Query("select key,user,amount,side,pollkey from bid where pollkey=$1")
    Flux<Bid> findByPollKey(String pollKey);


}
