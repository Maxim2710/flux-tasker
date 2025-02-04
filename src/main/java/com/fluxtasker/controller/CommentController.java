package com.fluxtasker.controller;

import com.fluxtasker.dto.CommentCreateDTO;
import com.fluxtasker.model.Comment;
import com.fluxtasker.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/api/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping
    public Mono<ResponseEntity<Comment>> createComment(@RequestBody CommentCreateDTO createDTO) {
        return commentService.createComment(
                createDTO.getTaskId(),
                createDTO.getText())
                .map(comment -> ResponseEntity.status(HttpStatus.CREATED).body(comment));
    }

    @GetMapping(path = "/{taskId}")
    public Mono<ResponseEntity<Flux<Comment>>> getCommentsByTaskId(@PathVariable Long taskId) {
        Flux<Comment> commentsFlux = commentService.getCommentsByTaskId(taskId);
        return commentsFlux.hasElements()
                .flatMap(hasComment -> {
                    if (hasComment) {
                        return Mono.just(ResponseEntity.ok(commentsFlux));
                    } else {
                        return Mono.just(ResponseEntity.notFound().build());
                    }
                });
    }
}
