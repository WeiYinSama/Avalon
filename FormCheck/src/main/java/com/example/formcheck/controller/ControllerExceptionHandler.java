package com.example.formcheck.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @Autowired
    private HttpServletResponse httpServletResponse;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Object methodArgumentNotValidException(MethodArgumentNotValidException e){

        StringBuffer sb =new StringBuffer();
        for(FieldError error:e.getBindingResult().getFieldErrors()){
            sb.append(error.getDefaultMessage());
            sb.append(";");
        }
//        httpServletResponse.setContentType("application/json;UTF-8");
        return sb.toString();
    }
}
