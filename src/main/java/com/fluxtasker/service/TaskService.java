package com.fluxtasker.service;

import com.fluxtasker.dto.TaskCreateDTO;
import com.fluxtasker.exception.TaskCreationException;
import com.fluxtasker.model.Task;
import com.fluxtasker.model.enums.TaskStatus;
import com.fluxtasker.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public Mono<Task> createTask(TaskCreateDTO taskDTO) {
        Task task = new Task();
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setStatus(TaskStatus.NEW);

        return taskRepository.save(task)
                .onErrorMap(error -> new TaskCreationException("Ошибка при создании задачи"));
    }
}
