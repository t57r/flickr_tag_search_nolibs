package com.flickrsearch.util.network;

public class Result<T> {
    private final T value;
    private final Exception exception;

    private Result(T value, Exception exception) {
        this.value = value;
        this.exception = exception;
    }

    Result(T value) {
        this(value, null);
    }

    Result(Exception exception) {
        this(null, exception);
    }

    public T getValue() {
        return value;
    }

    public Exception getException() {
        return exception;
    }
}