package com.veglad.servicesmarthome.dto.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class ApiResponse<T> {

    private T data;

    private ApiError error;

    public static  <T> ApiResponse<T> of(ApiError error) {
        return new ApiResponse(null, error);
    }
}
