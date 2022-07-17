package gov.service.vic.demo.rest.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String entityId) {
        super("Resource with ID = " + entityId + " not found. Might wanna double check your payload.");
    }
}
