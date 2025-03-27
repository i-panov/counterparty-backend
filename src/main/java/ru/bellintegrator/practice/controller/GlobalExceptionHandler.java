package ru.bellintegrator.practice.controller;

import org.slf4j.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.bellintegrator.practice.util.ExceptionLogBuilder;
import ru.bellintegrator.practice.view.*;

import java.util.stream.Stream;

import static ru.bellintegrator.practice.view.ResultView.*;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResultView<Object> processError(Exception e) {
        ExceptionLogBuilder.log(log, e);
        return error(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultView<Object> processValidationError(MethodArgumentNotValidException e) {
        ExceptionLogBuilder.log(log, e);
        Stream<ValidationErrorView> errors = e.getBindingResult().getFieldErrors().stream().map(x -> new ValidationErrorView(x.getField(), x.getDefaultMessage()));
        return error(errors);
    }
}
