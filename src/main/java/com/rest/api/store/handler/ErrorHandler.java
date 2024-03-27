package com.rest.api.store.handler;

import com.rest.api.store.dto.ErrorResponse;
import com.rest.api.store.exception.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Log4j2
@ControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception ex) {
        log.error("Exception intercepted: caused by " + ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(StoreGenericException.class)
    public ResponseEntity<Object> handleStoreGenericException(Exception ex) {
        log.error("Exception intercepted: caused by " + ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(StoreBusinessException.class)
    public ResponseEntity<Object> handleStoreBusinessException(Exception ex) {
        log.warn("Exception intercepted: caused by " + ex);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(StoreNotFoundException.class)
    public ResponseEntity<Object> handleStoreRegisterNotFoundException(Exception ex) {
        log.warn("Exception intercepted: caused by " + ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleStoreAccessDeniedException(Exception ex) {
        log.warn("Exception intercepted: caused by " + ex);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    public ResponseEntity<Object> handleStoreAccessAuthenticationCredentialsNotFoundException(Exception ex) {
        log.warn("Exception intercepted: caused by " + ex);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("There was an error validating the token"));
    }

    @ExceptionHandler(StoreInvalidCredentialsException.class)
    public ResponseEntity<Object> handleStoreAccessStoreInvalidCredentialsException(Exception ex) {
        log.warn("Exception intercepted: caused by " + ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Invalid customername/password!"));
    }

    @ExceptionHandler(CustomerAlreadyExistsException.class)
    public ResponseEntity<Object> handleCustomerAlreadyExistsException(Exception ex) {
        log.warn("Customer already exists " + ex);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse("Customer already exists!"));
    }
    @ExceptionHandler(ProductUnavailableException.class)
    public ResponseEntity<Object> handleProductUnavailableException(Exception ex) {
        log.warn("Product is unavailable " + ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Product is unavailable!"));
    }
}
