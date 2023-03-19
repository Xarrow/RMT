package io.github.xarrow.rmt.api.exception;

public class TerminalProcessException extends Exception {
    public TerminalProcessException() {
    }

    public TerminalProcessException(String message) {
        super(message);
    }

    public TerminalProcessException(String message, Throwable cause) {
        super(message, cause);
    }

    public TerminalProcessException(Throwable cause) {
        super(cause);
    }

    public TerminalProcessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
