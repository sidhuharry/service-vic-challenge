package gov.service.vic.demo.rest.controller;


import gov.service.vic.demo.rest.exception.InvalidRequestException;
import gov.service.vic.demo.rest.exception.InternalErrorException;
import gov.service.vic.demo.rest.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ErrorMapperAdvice {

    @ResponseBody
    @ExceptionHandler(InvalidRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String badRequest(InvalidRequestException invalidRequestException) {
        return invalidRequestException.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(InternalErrorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    String serverError(InternalErrorException internalErrorException) {
        return internalErrorException.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String notFound(ResourceNotFoundException resourceNotFoundException) {
        return resourceNotFoundException.getMessage();
    }

}
