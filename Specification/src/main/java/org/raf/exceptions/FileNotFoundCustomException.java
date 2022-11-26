package org.raf.exceptions;

public class FileNotFoundCustomException extends RuntimeException{
    public FileNotFoundCustomException(String errorMessage) {
        super(errorMessage);
    }
}
