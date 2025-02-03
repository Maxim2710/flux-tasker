package com.fluxtasker.config;

import com.fluxtasker.model.Comment;
import org.reactivestreams.Publisher;
import org.springframework.data.r2dbc.mapping.event.BeforeConvertCallback;
import org.springframework.data.relational.core.sql.SqlIdentifier;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Component
public class CommentBeforeConvertCallback implements BeforeConvertCallback<Comment> {

    @Override
    public Mono<Comment> onBeforeConvert(Comment comment, SqlIdentifier table) {
        LocalDateTime now = LocalDateTime.now();
        if (comment.getCreatedAt() == null) {
            comment.setCreatedAt(now);
        }

        return Mono.just(comment);
    }
}
