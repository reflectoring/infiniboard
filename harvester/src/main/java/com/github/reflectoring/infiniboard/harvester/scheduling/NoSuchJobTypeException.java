package com.github.reflectoring.infiniboard.harvester.scheduling;

/**
 * thrown if an job should be scheduled but its type was not registered
 */
public class NoSuchJobTypeException extends RuntimeException {

    /**
     * creates the exception
     *
     * @param message
     *         problem details
     */
    public NoSuchJobTypeException(String message) {
        super(message);
    }
}
