package org.haw.cas.PlaceOfResidenceAnalyzer.Implementation.Usecases.Exceptions;

/**
 * Created with IntelliJ IDEA.
 * User: N3trunner
 * Date: 09.12.13
 * Time: 22:38
 * To change this template use File | Settings | File Templates.
 */
public class UnknownServerErrorException extends Throwable {
    public UnknownServerErrorException() {
    }

    public UnknownServerErrorException(String message) {
        super(message);
    }

    public UnknownServerErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownServerErrorException(Throwable cause) {
        super(cause);
    }
}
