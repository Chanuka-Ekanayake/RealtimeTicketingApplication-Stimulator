package org.example.ticketingapplication.exception;

/**
 -  Custom exception for unavailable tickets

 */

public class NoTicketAvailableException extends RuntimeException {

    public NoTicketAvailableException() {
        super("No tickets available at the moment.");
    }

    public NoTicketAvailableException(String message) {
        super(message);
    }
}
