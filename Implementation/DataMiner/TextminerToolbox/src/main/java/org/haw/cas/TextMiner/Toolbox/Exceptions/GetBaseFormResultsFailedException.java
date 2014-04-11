package org.haw.cas.TextMiner.Toolbox.Exceptions;

/**
 * Created with IntelliJ IDEA.
 * User: N3trunner
 * Date: 26.11.13
 * Time: 18:58
 * To change this template use File | Settings | File Templates.
 */
public class GetBaseFormResultsFailedException extends Exception {
    public GetBaseFormResultsFailedException() {
    }

    public GetBaseFormResultsFailedException(String message) {
        super(message);
    }

    public GetBaseFormResultsFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public GetBaseFormResultsFailedException(Throwable cause) {
        super(cause);
    }
}
