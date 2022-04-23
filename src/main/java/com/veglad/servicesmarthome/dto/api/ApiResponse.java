package com.veglad.servicesmarthome.dto.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Nonnull;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class ApiResponse<T> {

    private T data;

    private ApiError error;

    public static  <T> ApiResponse<T> of(ApiError error) {
        return new ApiResponse(null, error);
    }

    public static <T> ApiResponse<T> of(T data) {
        Objects.requireNonNull(data, "data is null");
        return new ApiResponse<>(data, null);
    }
}
