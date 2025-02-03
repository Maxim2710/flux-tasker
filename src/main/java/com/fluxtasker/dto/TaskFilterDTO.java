package com.fluxtasker.dto;

import com.fluxtasker.model.enums.TaskStatus;
import lombok.Data;

import java.util.List;

@Data
public class TaskFilterDTO {
    private List<TaskStatus> statuses;
    private int page = 0;
    private int size = 10;
}
