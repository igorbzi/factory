package com.projedata.factory.exception;

public class ResourceInUseException extends RuntimeException {

    public ResourceInUseException(String resource, String usedBy) {
        super(resource + " cannot be deleted because it is used by one or more " + usedBy + ".");
    }
}