package br.com.raphaelcampean.todolist.errors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleHttpMessageNotReadableException(Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
