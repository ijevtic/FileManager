package org.raf.exceptions;

public class FileNotFoundCustomException extends Exception{
    public FileNotFoundCustomException(String errorMessage) {
        super(errorMessage);
    }
}
