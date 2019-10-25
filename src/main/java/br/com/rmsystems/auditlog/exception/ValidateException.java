package br.com.rmsystems.auditlog.exception;

/**
 * Runtime Exception
 */
public class ValidateException extends RuntimeException {

    /**
     *
     * @param message the error message
     */
    public ValidateException(String message) {
        super(message);
    }
}
