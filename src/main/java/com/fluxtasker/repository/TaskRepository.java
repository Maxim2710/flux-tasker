package com.fluxtasker.repository;

import com.fluxtasker.model.Task;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

@EnableR2dbcRepositories
public interface TaskRepository extends ReactiveCrudRepository<Task, Long> {
}
