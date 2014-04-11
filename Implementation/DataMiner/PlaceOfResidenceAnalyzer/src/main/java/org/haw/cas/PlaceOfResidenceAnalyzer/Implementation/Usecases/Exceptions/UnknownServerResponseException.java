package org.haw.cas.PlaceOfResidenceAnalyzer.Implementation.Usecases.Exceptions;

/**
 * Created with IntelliJ IDEA.
 * User: N3trunner
 * Date: 09.12.13
 * Time: 22:39
 * To change this template use File | Settings | File Templates.
 */
public class UnknownServerResponseException extends Throwable {
    public UnknownServerResponseException() {
    }

    public UnknownServerResponseException(String message) {
        super(message);
    }

    public UnknownServerResponseException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownServerResponseException(Throwable cause) {
        super(cause);
    }
}
