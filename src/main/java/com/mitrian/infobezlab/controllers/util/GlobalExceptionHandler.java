package com.mitrian.infobezlab.controllers.util;

import com.mitrian.infobezlab.exceptions.ItemValidateException;
import com.mitrian.infobezlab.exceptions.UserAbsenceException;
import com.mitrian.infobezlab.exceptions.UserCredentialsException;
import com.mitrian.infobezlab.exceptions.UserValidateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserCredentialsException.class)
    public ResponseEntity<String> handleInvalidCredentials(UserCredentialsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UserAbsenceException.class)
    public ResponseEntity<String> handleUsernameAlreadyExists(UserAbsenceException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ItemValidateException.class)
    public ResponseEntity<String> handleInvalidItemData(ItemValidateException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserValidateException.class)
    public ResponseEntity<String> handleInvalidItemData(UserValidateException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}