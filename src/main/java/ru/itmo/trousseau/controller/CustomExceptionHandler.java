package ru.itmo.trousseau.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.itmo.trousseau.exception.ConflictException;
import ru.itmo.trousseau.exception.ForbiddenException;
import ru.itmo.trousseau.exception.NotFoundException;

/**
 * Обработчик всех кастомных исключений приложения
 */
@ControllerAdvice
public class CustomExceptionHandler {

    /**
     * Обработчик исключений, порождающих статус 403 FORBIDDEN
     */
    @ExceptionHandler(ForbiddenException.class)
    public String handleForbiddenException(ForbiddenException e, Model model) {
        model.addAttribute("error", e);
        return "errors/403";
    }

    /**
     * Обработчик исключений, порождающих статус 404 NOT FOUND
     */
    @ExceptionHandler(NotFoundException.class)
    public String handleNotFoundException(NotFoundException e, Model model) {
        model.addAttribute("error", e);
        return "errors/404";
    }

    /**
     * Обработчик исключений, порождающих статус 409 CONFLICT
     */
    @ExceptionHandler(ConflictException.class)
    public String handleConflictException(ConflictException e, Model model) {
        model.addAttribute("error", e);
        return "errors/409";
    }
}
