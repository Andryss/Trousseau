package ru.itmo.trousseau.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.itmo.trousseau.exception.ForbiddenException;
import ru.itmo.trousseau.exception.NotFoundException;

/**
 * Обработчик всех кастомных исключений приложения
 */
@ControllerAdvice
public class CustomExceptionHandler {

    /**
     * Обработчик исключений, порождающих статус 404 NOT_FOUND
     */
    @ExceptionHandler(NotFoundException.class)
    public String handleNotFoundException() {
        return "errors/404";
    }

    /**
     * Обработчик исключений, порождающих статус 403 FORBIDDEN
     */
    @ExceptionHandler(ForbiddenException.class)
    public String handleForbiddenException() {
        return "errors/403";
    }
}
