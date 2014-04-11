package org.haw.cas.PlaceOfResidenceAnalyzer.Implementation.Usecases.Exceptions;

/**
 * Created with IntelliJ IDEA.
 * User: N3trunner
 * Date: 09.12.13
 * Time: 22:33
 * To change this template use File | Settings | File Templates.
 */
public class NoResultsFoundException extends Throwable {
    public NoResultsFoundException() {
    }

    public NoResultsFoundException(String message) {
        super(message);
    }

    public NoResultsFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoResultsFoundException(Throwable cause) {
        super(cause);
    }
}
