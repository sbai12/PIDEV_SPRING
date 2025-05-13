package edu.esprit.payment.config;

import edu.esprit.payment.dto.error.ErrorDTO;
import edu.esprit.payment.dto.error.ErrorsDTO;
import edu.esprit.payment.exeception.BadRequestException;
import edu.esprit.payment.exeception.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.AccessDeniedException;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ApplicationExceptionHandler {

    private ResponseEntity<ErrorsDTO> buildErrorResponse(
            HttpStatus status, String message, ErrorDTO.ErrorTypeEnum type) {
        return ResponseEntity.status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        ErrorsDTO.builder()
                                .errors(
                                        List.of(
                                                ErrorDTO.builder()
                                                        .errorCode(status.value())
                                                        .errorLevel(ErrorDTO.ErrorLevelEnum.ERROR)
                                                        .errorType(type)
                                                        .errorMessage(status.getReasonPhrase() + ": " + message)
                                                        .build()))
                                .build());
    }

    private void logError(String msg, HttpServletRequest req, Exception ex) {
        log.error(msg + " '{}' : {}", req.getRequestURI(), ex.getMessage(), ex);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorsDTO> handleResponseStatusException(
            ResponseStatusException ex, HttpServletRequest req) {
        logError("Response status exception", req, ex);
        return buildErrorResponse(HttpStatus.valueOf(ex.getStatusCode().value()), ex.getMessage(), ErrorDTO.ErrorTypeEnum.FUNCTIONAL);
    }

    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            ConstraintViolationException.class,
            MethodArgumentTypeMismatchException.class,
            BadRequestException.class,
            MissingServletRequestParameterException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorsDTO> handleBadRequestExceptions(
            Exception ex, HttpServletRequest req) {
        logError("Invalid argument exception", req, ex);

        String message = ex instanceof MissingServletRequestParameterException mEx
                ? "Missing parameter " + mEx.getParameterName() + ": " + mEx.getMessage()
                : ex.getMessage();

        return buildErrorResponse(BAD_REQUEST, message, ErrorDTO.ErrorTypeEnum.TECHNICAL);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorsDTO> handleNotFoundException(
            NotFoundException ex, HttpServletRequest req) {
        logError("Not found exception", req, ex);
        return buildErrorResponse(NOT_FOUND, ex.getMessage(), ErrorDTO.ErrorTypeEnum.FUNCTIONAL);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<ErrorsDTO> handleAccessDeniedException(
            AccessDeniedException ex, HttpServletRequest req) {
        logError("Access denied", req, ex);
        return buildErrorResponse(FORBIDDEN, ex.getMessage(), ErrorDTO.ErrorTypeEnum.TECHNICAL);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorsDTO> handleUnknownException(Exception ex, HttpServletRequest req) {
        logError("Unexpected exception", req, ex);
        return buildErrorResponse(INTERNAL_SERVER_ERROR, ex.getMessage(), ErrorDTO.ErrorTypeEnum.TECHNICAL);
    }
}
