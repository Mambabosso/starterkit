package com.github.mambabosso.starterkit.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public final class Result<T extends Serializable> implements Serializable {

    @JsonProperty
    private boolean success;

    @JsonProperty
    private T value;

    @JsonProperty
    private Exception error;

    @JsonProperty
    private String errorMessage;

    private Result() {
    }

    public static <T extends Serializable> Result<T> success(final T value) {
        Result<T> result = new Result<>();
        result.setSuccess(true);
        result.setValue(value);
        return result;
    }

    public static <T extends Serializable> Result<T> failure(final Exception error) {
        Result<T> result = new Result<>();
        result.setSuccess(false);
        result.setError(error);
        return result;
    }

    public static <T extends Serializable> Result<T> failure(final String errorMessage) {
        Result<T> result = new Result<>();
        result.setSuccess(false);
        result.setErrorMessage(errorMessage);
        return result;
    }

    public static <T extends Serializable> Result<T> failure(final Exception error, final String errorMessage) {
        Result<T> result = new Result<>();
        result.setSuccess(false);
        result.setError(error);
        result.setErrorMessage(errorMessage);
        return result;
    }

}
