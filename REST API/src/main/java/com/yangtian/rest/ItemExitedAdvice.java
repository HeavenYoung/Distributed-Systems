package com.yangtian.rest;

/**
 * @author Yang Tian
 * @date 22/02/2021 01:03
 */

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ItemExitedAdvice {

    @ResponseBody
    @ExceptionHandler(ItemExistedException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String employeeNotFoundHandler(ItemExistedException ex) {
        return ex.getMessage();
    }
}
