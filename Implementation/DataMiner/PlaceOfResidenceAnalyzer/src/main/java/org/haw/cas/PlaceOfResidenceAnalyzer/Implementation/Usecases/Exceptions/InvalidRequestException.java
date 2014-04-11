package org.haw.cas.PlaceOfResidenceAnalyzer.Implementation.Usecases.Exceptions;

/**
 * Created with IntelliJ IDEA.
 * User: N3trunner
 * Date: 09.12.13
 * Time: 22:37
 * To change this template use File | Settings | File Templates.
 */
public class InvalidRequestException extends Throwable {
    public InvalidRequestException() {
    }

    public InvalidRequestException(String message) {
        super(message);
    }

    public InvalidRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidRequestException(Throwable cause) {
        super(cause);
    }
}
