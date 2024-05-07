package ru.anykeyers.configurationservice.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.anykeyers.commonsapi.domain.dto.ApiError;

import java.util.HashMap;
import java.util.Map;

/**
 * Обработчик ошибок в приложении
 */
@Slf4j
@ControllerAdvice
@RestControllerAdvice
public class ExceptionHandlerProcessor {

    /**
     * Обработать исключение и выдать пользователю информацию об ошибке в формате JSON
     */
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    @ExceptionHandler(RuntimeException.class)
    public ApiError<String> handleException(RuntimeException ex) {
        log.error("Handle error: {}", ex.getMessage());
        return new ApiError<>(ex.getMessage());
    }

    /**
     * Обработать исключение валидации и выдать пользователю информацию об ошибке в формате JSON
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiError<?> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));
        return new ApiError<>(errors);
    }

}
