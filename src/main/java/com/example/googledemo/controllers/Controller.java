package com.example.googledemo.controllers;

import com.example.googledemo.model.Bid;
import com.example.googledemo.model.Comment;
import com.example.googledemo.model.Polls;
import com.example.googledemo.model.PollsReturn;
import com.example.googledemo.repository.BidRepository;
import com.example.googledemo.repository.CommentRepository;
import com.example.googledemo.repository.PollsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.websocket.server.PathParam;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class Controller {


    @Autowired
    PollsRepository pollsRepository;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    BidRepository bidRepository;


    @PostMapping("/api/poll")
    public Mono<String> poll(@RequestBody Polls polls) {
        return pollsRepository.save(polls).flatMap(x -> Mono.just(x.getKey()));
    }

    @GetMapping("/api/bid/{pollkey}/{side}")
    public Mono<Bid> bid(@PathVariable String pollkey, @PathVariable("side") String side,@AuthenticationPrincipal Principal principal ) {
        Bid bid = new Bid();
        bid.setUsername(principal==null?"User":((OAuth2AuthenticationToken) principal).getPrincipal().getAttribute("name"));
        bid.setPollkey(pollkey);
        bid.setSide(Boolean.parseBoolean(side));
        bid.setAmount(0.0);
        return bidRepository.save(bid).flatMap(x -> Mono.just(x));
    }

    @PostMapping("/api/comment")
    public Mono<String> comment(@RequestBody Comment comment, @AuthenticationPrincipal Principal principal) {
        comment.setUsername(principal.getName());
        return commentRepository.save(comment).flatMap(x -> Mono.just(x.getKey()));
    }

    @GetMapping("/api/poll")
    public Flux<PollsReturn> getPoll() {
        List<PollsReturn> pollsReturn = new ArrayList();
        return pollsRepository.findAll().flatMap(
                poll -> {
                    PollsReturn pollReturn = new PollsReturn();
                    pollReturn.setPolls(poll);
                    return bidRepository.findByPollKey(poll.getKey()).collectList().flatMap(
                            bids -> {
                                pollReturn.setBids(bids);
                                return Mono.empty();
                            }
                    ).then(
                            commentRepository.findByPollKey(poll.getKey()).collectList().flatMap(
                                    comment -> {
                                        pollReturn.setComments(comment);
                                        return Mono.empty();
                                    }
                            )).then(Mono.just(pollReturn));

                }
        ).sort((obj1, obj2) -> obj1.getPolls().getKey().compareTo(obj2.getPolls().getKey()));
    }

    @GetMapping("/bid")
    public Flux<Bid> getBids() {
        return bidRepository.findAll();
    }

    @GetMapping("/comment")
    public Flux<Comment> getComments() {
        return commentRepository.findAll();
    }

}
