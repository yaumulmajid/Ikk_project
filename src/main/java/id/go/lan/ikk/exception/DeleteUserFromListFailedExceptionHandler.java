package id.go.lan.ikk.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DeleteUserFromListFailedExceptionHandler {
    @ExceptionHandler(DeleteUserFromListFailedException.class)
    public ResponseEntity<Object> handleException(DeleteUserFromListFailedException exception) {
        return new ResponseEntity<>(
                exception.getMessage(),
                HttpStatus.BAD_REQUEST);
    }
}
