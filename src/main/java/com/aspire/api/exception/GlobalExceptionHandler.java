package com.aspire.api.exception;

import com.aspire.api.dto.CustomResponse;

import org.springframework.core.convert.ConversionFailedException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.elasticsearch.UncategorizedElasticsearchException;
import org.springframework.data.elasticsearch.core.convert.ConversionException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, List<String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        Map<String, List<String>> error = new HashMap<>();
        List<String> errorList = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(errors -> errorList.add(errors.getDefaultMessage()));
        error.put("message",errorList);
        return error;
    }

    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public CustomResponse handleDuplicateKeyException(DuplicateKeyException ex){
        return new CustomResponse("Email or mobile already exists");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CustomResponse handleIllegalArgumentException(IllegalArgumentException ex){
        return new CustomResponse(ex.getMessage());
    }


    @ExceptionHandler(ManagerAlreadyExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CustomResponse handleManagerAlreadyExistException(ManagerAlreadyExistException ex){
        return new CustomResponse(ex.getMessage());
    }

    @ExceptionHandler(ManagerDoesNotExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CustomResponse handleManagerDoesNotExistException(ManagerDoesNotExistException ex){
        return new CustomResponse("Manager with given id does not exist");
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CustomResponse handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex){
        return new CustomResponse(ex.getName()+" only accepts a Integer");
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CustomResponse handleMissingServletRequestParameterException(MissingServletRequestParameterException ex){
        return new CustomResponse(ex.getParameterName()+" is required");
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public CustomResponse handleDataIntegrityViolationException(DataIntegrityViolationException ex){
        return new CustomResponse("Email or mobile already exists");
    }

    @ExceptionHandler(UncategorizedElasticsearchException.class)
    public CustomResponse handleUncategorizedElasticsearchException(UncategorizedElasticsearchException ex){
        return new CustomResponse(ex.getMessage()+ex.getResponseBody());
    }

    @ExceptionHandler(ConversionFailedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CustomResponse handleConversionFailedException(UncategorizedElasticsearchException ex){
        return new CustomResponse(ex.getMessage()+ex.getResponseBody());
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CustomResponse handleException(Exception ex){
        return new CustomResponse(ex.getMessage());
    }
}
