package com.mvp1.whatiread.exception;

import com.mvp1.whatiread.dto.ApiResponse;
import com.mvp1.whatiread.dto.ExceptionResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class RestControllerExceptionHandler {

  public ResponseEntity<ApiResponse> resolveException(WhatIReadException exception) {
    String message = exception.getMessage();
    HttpStatus status = exception.getStatus();
    ApiResponse apiResponse = new ApiResponse();
    apiResponse.setSuccess(Boolean.FALSE);
    apiResponse.setMessage(message);
    return new ResponseEntity<>(apiResponse, status);
  }

  @ExceptionHandler(UnauthorizedException.class)
  @ResponseBody
  @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
  public ResponseEntity<ApiResponse> resolveException(UnauthorizedException exception) {
    ApiResponse apiResponse = exception.getApiResponse();
    return new ResponseEntity<>(apiResponse, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(BadRequestException.class)
  @ResponseBody
  public ResponseEntity<ApiResponse> resolveException(BadRequestException exception) {
    ApiResponse apiResponse = exception.getApiResponse();
    return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  @ResponseBody
  public ResponseEntity<ApiResponse> resolveException(ResourceNotFoundException exception) {
    ApiResponse apiResponse = exception.getApiResponse();
    return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(AccessDeniedException.class)
  @ResponseBody
  public ResponseEntity<ApiResponse> resolveException(AccessDeniedException exception) {
    ApiResponse apiResponse = exception.getApiResponse();
    return new ResponseEntity<>(apiResponse, HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler({MethodArgumentNotValidException.class})
  @ResponseBody
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<ExceptionResponse> resolveException(MethodArgumentNotValidException ex) {
    List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
    List<String> messages = fieldErrors.stream()
        .map(error -> error.getField() + " - " + error.getDefaultMessage())
        .toList();
    return new ResponseEntity<>(
        new ExceptionResponse(messages, HttpStatus.BAD_REQUEST.getReasonPhrase(),
            HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler({MethodArgumentTypeMismatchException.class})
  @ResponseBody
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<ExceptionResponse> resolveException(
      MethodArgumentTypeMismatchException ex) {
    String message = "Parameter '" + ex.getParameter().getParameterName() + "' must be '"
        + Objects.requireNonNull(ex.getRequiredType()).getSimpleName() + "'";
    List<String> messages = new ArrayList<>(1);
    messages.add(message);
    return new ResponseEntity<>(
        new ExceptionResponse(messages, HttpStatus.BAD_REQUEST.getReasonPhrase(),
            HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
  @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
  @ResponseBody
  public ResponseEntity<ExceptionResponse> resolveException(
      HttpRequestMethodNotSupportedException ex) {
    String message =
        "Request method '" + ex.getMethod() + "' not supported. List of all supported methods - "
            + ex.getSupportedHttpMethods();
    List<String> messages = new ArrayList<>(1);
    messages.add(message);
    return new ResponseEntity<>(
        new ExceptionResponse(messages, HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase(),
            HttpStatus.METHOD_NOT_ALLOWED.value()), HttpStatus.METHOD_NOT_ALLOWED);
  }

  @ExceptionHandler({HttpMessageNotReadableException.class})
  @ResponseBody
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<ExceptionResponse> resolveException(HttpMessageNotReadableException ex) {
    String message = "Please provide Request Body in valid JSON format";
    List<String> messages = new ArrayList<>(1);
    messages.add(message);
    return new ResponseEntity<>(
        new ExceptionResponse(messages, HttpStatus.BAD_REQUEST.getReasonPhrase(),
            HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler({InternalServerError.class})
  @ResponseBody
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseEntity<ApiResponse> resolveException(InternalServerError exception) {
    System.out.println("Error in internal server error");
    String message = exception.getMessage();
    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    ApiResponse apiResponse = new ApiResponse();
    apiResponse.setSuccess(Boolean.FALSE);
    apiResponse.setMessage(message);
    return new ResponseEntity<>(apiResponse, status);
  }
}
