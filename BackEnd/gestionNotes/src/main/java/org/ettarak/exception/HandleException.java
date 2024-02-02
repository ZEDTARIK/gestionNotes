package org.ettarak.exception;

import jakarta.persistence.NoResultException;
import jakarta.servlet.ServletException;
import lombok.extern.slf4j.Slf4j;
import org.ettarak.models.HttpResponse;
import org.ettarak.utils.DateUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class HandleException extends ResponseEntityExceptionHandler  {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error(exception.getMessage());

        List<FieldError> fieldErrors= exception.getBindingResult().getFieldErrors();
        String fieldMessage= fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(", "));

        return new ResponseEntity<>(
                HttpResponse.builder()
                        .reason("Invalid fields: " + fieldMessage)
                        .developerMessage(exception.getMessage())
                        .statusCode(status.value())
                        .status((HttpStatus) status)
                        .timeStamp(LocalDateTime.now().format(DateUtil.dateTimeFormatter()))
                        .build(),
                status);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception exception, @Nullable Object body, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return new ResponseEntity<>(
                HttpResponse.builder()
                        .reason("Internal Error " + exception.getMessage())
                        .developerMessage(exception.getMessage())
                        .statusCode(status.value())
                        .status((HttpStatus) status)
                        .timeStamp(LocalDateTime.now().format(DateUtil.dateTimeFormatter()))
                        .build(),
                status);
        }

        @ExceptionHandler(IllegalStateException.class)
        public ResponseEntity<HttpResponse<?>> illegalStateException (IllegalStateException exception) {
            return createHttpErrorResponse(exception.getMessage(), exception);
        }

        @ExceptionHandler(NoteNotFoundException.class)
        public ResponseEntity<HttpResponse<?>> noteNotFoundException (NoteNotFoundException exception) {
            return createHttpErrorResponse(exception.getMessage(), exception);
        }

        @ExceptionHandler(NoResultException.class)
        public ResponseEntity<HttpResponse<?>> noResultException (NoResultException exception) {
            return createHttpErrorResponse(exception.getMessage(), exception);
        }

        @ExceptionHandler(ServletException.class)
        public ResponseEntity<HttpResponse<?>> servletException (ServletException exception) {
            return createHttpErrorResponse(exception.getMessage(), exception);
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<HttpResponse<?>> exception (Exception exception) {
            return createHttpErrorResponse(exception.getMessage(), exception);
        }

        private ResponseEntity<HttpResponse<?>> createHttpErrorResponse(String reason, Exception exception){
            return new ResponseEntity<>(
                    HttpResponse.builder()
                            .reason(reason)
                            .developerMessage(exception.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .timeStamp(LocalDateTime.now().format(DateUtil.dateTimeFormatter()))
                            .build(),
                    HttpStatus.BAD_REQUEST);
        }
}
