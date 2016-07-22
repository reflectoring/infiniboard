package com.github.reflectoring.infiniboard.harvester.scheduling;

/**
 * thrown if an job type identifier is registered more than once
 */
public class JobTypeAlreadyRegisteredException extends RuntimeException {

    /**
     * creates the exception
     *
     * @param message
     *         problem details
     */
    public JobTypeAlreadyRegisteredException(String message) {
        super(message);
    }
}
