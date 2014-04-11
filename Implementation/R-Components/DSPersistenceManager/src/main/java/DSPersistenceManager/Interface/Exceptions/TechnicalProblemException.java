package DSPersistenceManager.Interface.Exceptions;

/**
 * User: Jason Wilmans
 * Date: 10.10.13
 * Time: 15:39
 */


/**
 * This exception is thrown, if a technical problem occurs while executing any persistence manager operations.
 */
public class TechnicalProblemException extends Exception{

    private ErrorType type;

    public TechnicalProblemException(ErrorType type, String message, Throwable cause) {
        super(message, cause);
        this.type = type;
    }
}
