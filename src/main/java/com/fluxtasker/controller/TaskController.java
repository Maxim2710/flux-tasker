package com.fluxtasker.controller;

import com.fluxtasker.dto.TaskCreateDTO;
import com.fluxtasker.dto.TaskFilterDTO;
import com.fluxtasker.dto.TaskUpdateDTO;
import com.fluxtasker.model.Task;
import com.fluxtasker.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping
    public Mono<ResponseEntity<Task>> createTask(@RequestBody TaskCreateDTO taskDTO) {
        return taskService.createTask(taskDTO)
                .map(task -> ResponseEntity.status(HttpStatus.CREATED).body(task));
    }

    @GetMapping
    public Mono<ResponseEntity<Flux<Task>>> getTasks(@RequestBody Mono<TaskFilterDTO> filterDTOMono) {
        return filterDTOMono.flatMap(filterDTO ->
                Mono.just(
                        ResponseEntity.status(HttpStatus.OK)
                                .body(taskService.getTasks(
                                        filterDTO.getStatuses(),
                                        filterDTO.getPage(),
                                        filterDTO.getSize()

                                ))
                )
        );
    }

    @GetMapping(path = "/{id}")
    public Mono<ResponseEntity<Task>> getUserById(@PathVariable Long id) {
        return taskService.getTaskById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping(path = "/update")
    public Mono<ResponseEntity<String>> updateTask(@RequestBody TaskUpdateDTO updateDTO) {
        return taskService.updateTask(updateDTO)
                .map(updatedTask -> ResponseEntity.ok("Задача успешно изменена"))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping(path = "/delete/{id}")
    public Mono<ResponseEntity<String>> deleteTask(@PathVariable Long id) {
        return taskService.deleteTaskById(id)
                .flatMap(deletedTask ->{
                    if (deletedTask) {
                        return Mono.just(ResponseEntity.ok("Задача успешно удалена"));
                    } else {
                        return Mono.just(ResponseEntity.notFound().build());
                    }
                });
    }
}
