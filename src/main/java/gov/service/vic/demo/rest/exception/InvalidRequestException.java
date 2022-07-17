package gov.service.vic.demo.rest.exception;

public class InvalidRequestException extends RuntimeException {
    public InvalidRequestException(String reason) {
        super("Bad request. Reason: " + reason);
    }
}
