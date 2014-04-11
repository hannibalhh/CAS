package org.haw.cas.PlaceOfResidenceAnalyzer.Implementation.Usecases.Exceptions;

/**
 * Created with IntelliJ IDEA.
 * User: N3trunner
 * Date: 09.12.13
 * Time: 22:36
 * To change this template use File | Settings | File Templates.
 */
public class DailyQueryLimitReachedException extends Throwable {
    public DailyQueryLimitReachedException() {
    }

    public DailyQueryLimitReachedException(String message) {
        super(message);
    }

    public DailyQueryLimitReachedException(String message, Throwable cause) {
        super(message, cause);
    }

    public DailyQueryLimitReachedException(Throwable cause) {
        super(cause);
    }
}
