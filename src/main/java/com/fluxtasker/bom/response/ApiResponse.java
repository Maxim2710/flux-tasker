package com.fluxtasker.bom.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private T data;
    private String error;

    public ApiResponse(T data) {
        this.data = data;
    }

    public ApiResponse(String error) {
        this.error = error;
    }
}

