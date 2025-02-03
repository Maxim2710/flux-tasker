package com.fluxtasker.dto;

import com.fluxtasker.model.enums.TaskStatus;
import lombok.Data;

@Data
public class TaskCreateDTO {
    private String title;
    private String description;
}
