package com.veglad.servicesmarthome.controller.advice;

import com.veglad.servicesmarthome.dto.api.ApiError;
import com.veglad.servicesmarthome.dto.api.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ApiResponse<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.warn(ex.getMessage());
        BindingResult bindingResult = ex.getBindingResult();

        if (bindingResult.hasGlobalErrors()) {
            return Optional.ofNullable(bindingResult.getGlobalError())
                    .map(it -> this.errorInfo(it.getObjectName(), it.getDefaultMessage()))
                    .get();
        } else {
            return Optional.ofNullable(bindingResult.getFieldError())
                    .map(it -> this.errorInfo(it.getObjectName(), it.getDefaultMessage()))
                    .get();
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public ApiResponse<Void> handleConstraintViolationException(ConstraintViolationException ex) {
        log.warn(ex.getMessage());

        Stream<ApiError> fieldErrors = ex.getConstraintViolations().stream()
                .map(err -> ApiError.builder()
                        .field(err.getPropertyPath().toString())
                        .message(err.getMessage() + " but got " + err.getInvalidValue())
                        .build());

        ApiError error = ApiError.builder()
                .message("Request validation failed")
                .errors(fieldErrors.collect(Collectors.toList())).build();

        return ApiResponse.of(error);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiResponse<Void> handleArgumentConversionException(IllegalArgumentException exception) {
        log.warn("Argument invalid", exception);
        return this.errorInfo("Bad request", exception.getMessage());
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ApiResponse<Void> handleException(Exception exception) {
        log.error(exception.getMessage(), exception);
        return ApiResponse.of(ApiError.of(exception.getMessage()));
    }

    private ApiResponse<Void> errorInfo(String name, String description) {
        return ApiResponse.of(
                ApiError.builder()
                        .message(name)
                        .description(description)
                        .build());
    }
}
