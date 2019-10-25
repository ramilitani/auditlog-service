package br.com.rmsystems.auditlog.exception;

public class InternalErrorException extends RuntimeException {

    /**
     *
     * @param message
     * @param ex
     */
    public InternalErrorException(String message, Throwable ex) {

        super(message, ex);
    }
}