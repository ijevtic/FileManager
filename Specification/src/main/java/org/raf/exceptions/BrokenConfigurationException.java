package org.raf.exceptions;

public class BrokenConfigurationException extends Exception{
    public BrokenConfigurationException(String errorMessage) {
        super(errorMessage);
    }
}
