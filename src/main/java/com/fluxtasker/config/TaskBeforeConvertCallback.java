package com.fluxtasker.config;

import com.fluxtasker.model.Task;
import org.reactivestreams.Publisher;
import org.springframework.data.r2dbc.mapping.event.BeforeConvertCallback;
import org.springframework.data.relational.core.sql.SqlIdentifier;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Component
public class TaskBeforeConvertCallback implements BeforeConvertCallback<Task> {

    @Override
    public Mono<Task> onBeforeConvert(Task task, SqlIdentifier table) {
        LocalDateTime now = LocalDateTime.now();

        if (task.getCreatedAt() == null) {
            task.setCreatedAt(now);
        }

        task.setUpdatedAt(now);
        return Mono.just(task);
    }
}
