package com.github.mambabosso.starterkit.error;

public final class Errors {

    public static final ErrorCode UNKNOWN = ErrorCode.create(0);

    // misc
    public static final ErrorCode INVALID_STRING = ErrorCode.create(101);
    public static final ErrorCode INVALID_USER = ErrorCode.create(102);
    public static final ErrorCode INVALID_NAME = ErrorCode.create(103);
    public static final ErrorCode INVALID_MAIL = ErrorCode.create(104);
    public static final ErrorCode INVALID_PASSWORD = ErrorCode.create(105);
    public static final ErrorCode INVALID_ROLE = ErrorCode.create(106);
    public static final ErrorCode INVALID_TOKEN = ErrorCode.create(107);

    public static final ErrorCode USER_NOT_FOUND = ErrorCode.create(201);
    public static final ErrorCode ROLE_NOT_FOUND = ErrorCode.create(202);


    // register
    public static final ErrorCode NAME_VALIDATION_FAIL = ErrorCode.create(1001);
    public static final ErrorCode MAIL_VALIDATION_FAIL = ErrorCode.create(1002);
    public static final ErrorCode PASSWORD_VALIDATION_FAIL = ErrorCode.create(1003);

    public static final ErrorCode NAME_ALREADY_TAKEN = ErrorCode.create(1010);
    public static final ErrorCode MAIL_ALREADY_TAKEN = ErrorCode.create(1011);

    // login
    public static final ErrorCode INVALID_CREDENTIALS = ErrorCode.create(2001);
    public static final ErrorCode TOKEN_EXPIRED = ErrorCode.create(2002);


}
