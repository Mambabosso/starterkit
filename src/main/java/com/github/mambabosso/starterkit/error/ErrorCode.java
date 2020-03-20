package com.github.mambabosso.starterkit.error;

import lombok.Data;

import java.io.Serializable;

@Data
public final class ErrorCode implements Serializable {

    private int code;

    private ErrorCode() {
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof ErrorCode) {
            ErrorCode e = (ErrorCode)other;
            return this.code == e.code;
        }
        return false;
    }

    public static ErrorCode create(int code) {
        ErrorCode error = new ErrorCode();
        error.setCode(code);
        return error;
    }

}
