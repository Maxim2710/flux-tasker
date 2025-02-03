package com.fluxtasker.controller;

import com.fluxtasker.dto.TaskCreateDTO;
import com.fluxtasker.model.Task;
import com.fluxtasker.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping
    public Mono<ResponseEntity<Task>> createTask(@RequestBody TaskCreateDTO taskDTO) {
        return taskService.createTask(taskDTO)
                .map(task -> ResponseEntity.status(HttpStatus.CREATED).body(task));
    }
}
