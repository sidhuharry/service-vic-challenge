package gov.service.vic.demo.rest.exception;

public class InternalErrorException extends RuntimeException {

    public InternalErrorException(String message) {
        super("Something went wrong on the server. So here's the stack trace: " + message);
    }

}
