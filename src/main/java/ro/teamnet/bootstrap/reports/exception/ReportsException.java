package ro.teamnet.bootstrap.reports.exception;

import ro.teamnet.bootstrap.exception.ApplicationException;

/**
 * @author Bogdan.Iancu
 * @version 1.0 Date: 11-Mar-15
 */

public class ReportsException extends ApplicationException {

    public ReportsException(String message) {
        super(message);
    }

    public ReportsException(String message, Throwable cause) {
        super(message, cause);
    }

}
