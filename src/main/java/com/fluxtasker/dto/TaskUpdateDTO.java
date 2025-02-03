package com.fluxtasker.dto;

import com.fluxtasker.model.enums.TaskStatus;
import lombok.Data;

@Data
public class TaskUpdateDTO {
    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
}
