package com.veglad.servicesmarthome.dto.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiError {

    private String field;

    private String message;

    private List<ApiError> errors;

    private String description;

    public static ApiError of(String message) {
        return ApiError.builder().message(message).build();
    }
}
