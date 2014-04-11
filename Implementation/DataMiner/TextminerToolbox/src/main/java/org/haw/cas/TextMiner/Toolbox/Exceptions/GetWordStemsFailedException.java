package org.haw.cas.TextMiner.Toolbox.Exceptions;

/**
 * Created with IntelliJ IDEA.
 * User: N3trunner
 * Date: 26.11.13
 * Time: 19:02
 * To change this template use File | Settings | File Templates.
 */
public class GetWordStemsFailedException extends Exception {
    public GetWordStemsFailedException() {
    }

    public GetWordStemsFailedException(String message) {
        super(message);
    }

    public GetWordStemsFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public GetWordStemsFailedException(Throwable cause) {
        super(cause);
    }

    public GetWordStemsFailedException(Exception e) {
    }
}
