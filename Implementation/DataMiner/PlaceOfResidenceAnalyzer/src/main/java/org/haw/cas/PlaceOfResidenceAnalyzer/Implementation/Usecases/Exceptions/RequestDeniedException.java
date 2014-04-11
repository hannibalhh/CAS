package org.haw.cas.PlaceOfResidenceAnalyzer.Implementation.Usecases.Exceptions;

/**
 * Created with IntelliJ IDEA.
 * User: N3trunner
 * Date: 09.12.13
 * Time: 22:36
 * To change this template use File | Settings | File Templates.
 */
public class RequestDeniedException extends Throwable {
    public RequestDeniedException() {
    }

    public RequestDeniedException(String message) {
        super(message);
    }

    public RequestDeniedException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequestDeniedException(Throwable cause) {
        super(cause);
    }
}
