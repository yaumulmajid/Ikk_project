package id.go.lan.ikk.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RandomizePolicySampleFailedExceptionHandler {
    @ExceptionHandler(RandomizePolicySampleFailedException.class)
    public ResponseEntity<Object> handleException(RandomizePolicySampleFailedException exception) {
        return new ResponseEntity<>(
                exception.getMessage(),
                HttpStatus.BAD_REQUEST);
    }
}
