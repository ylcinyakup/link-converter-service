package com.example.linkconverterservice.model.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serializable;

@ResponseStatus(HttpStatus.BAD_REQUEST)
@Getter
public class ResourceAlreadyExistException extends RuntimeException implements Serializable {
    private static final long serialVersionUID = 7482528512863709084L;
    private final String resourceName;
    private final String fieldName;
    private final Object fieldValue;

    public ResourceAlreadyExistException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s already exist with %s : '%s'", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public ResourceAlreadyExistException(String resourceName) {
        super(String.format("%s already exist", resourceName));
        this.resourceName = resourceName;
        this.fieldName = null;
        this.fieldValue = null;
    }

}