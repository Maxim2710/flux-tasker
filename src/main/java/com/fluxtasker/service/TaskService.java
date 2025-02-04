package com.fluxtasker.service;

import com.fluxtasker.dto.TaskCreateDTO;
import com.fluxtasker.dto.TaskUpdateDTO;
import com.fluxtasker.exception.TaskCreationException;
import com.fluxtasker.model.Task;
import com.fluxtasker.model.enums.TaskStatus;
import com.fluxtasker.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.util.List;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    private final Sinks.Many<Task> taskSink = Sinks.many().multicast().onBackpressureBuffer();

    public Mono<Task> createTask(TaskCreateDTO taskDTO) {
        Task task = new Task();
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setStatus(TaskStatus.NEW);

        return taskRepository.save(task)
                .doOnSuccess(taskSink::tryEmitNext)
                .onErrorMap(error -> new TaskCreationException("Ошибка при создании задачи"));
    }

    public Flux<Task> getTasks(List<TaskStatus> statuses, int page, int size) {
        Flux<Task> taskFlux;
        if (statuses == null || statuses.isEmpty()) {
            taskFlux = taskRepository.findAll();
        } else {
            taskFlux = taskRepository.findAll()
                    .filter(task -> statuses.contains(task.getStatus()));
        }

        return taskFlux.skip((long) page * size).take(size);
    }

    public Mono<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public Mono<Task> updateTask(TaskUpdateDTO updateDTO) {
        return taskRepository.findById(updateDTO.getId())
                .flatMap(existingTask -> {
                    if (updateDTO.getTitle() != null) {
                        existingTask.setTitle(updateDTO.getTitle());
                    }

                    if (updateDTO.getDescription() != null) {
                        existingTask.setDescription(updateDTO.getDescription());
                    }

                    if (updateDTO.getStatus() != null) {
                        existingTask.setStatus(updateDTO.getStatus());
                    }

                    return taskRepository.save(existingTask);
                })
                .doOnSuccess(taskSink::tryEmitNext);
    }

    public Mono<Boolean> deleteTaskById(Long id) {
        return taskRepository.findById(id)
                .flatMap(existingTask ->
                        taskRepository.delete(existingTask)
                                .then(Mono.just(true))
                .defaultIfEmpty(false));
    }

    public Flux<Task> streamTasks() {
        return taskSink.asFlux();
    }
}
