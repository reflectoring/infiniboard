package com.github.reflectoring.infiniboard.harvester.scheduling;

/**
 * thrown if an job should be scheduled that is already scheduled
 */
public class JobAlreadyScheduledException extends RuntimeException {

    /**
     * creates the exception
     *
     * @param message
     *         problem details
     */
    public JobAlreadyScheduledException(String message) {
        super(message);
    }
}
