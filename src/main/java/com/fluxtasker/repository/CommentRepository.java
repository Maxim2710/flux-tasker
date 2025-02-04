package com.fluxtasker.repository;

import com.fluxtasker.model.Comment;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

@EnableR2dbcRepositories
public interface CommentRepository extends ReactiveCrudRepository<Comment, Long> {

}
