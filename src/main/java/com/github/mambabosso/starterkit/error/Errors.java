package com.github.mambabosso.starterkit.error;

public final class Errors {

    public static final ErrorCode UNKNOWN = ErrorCode.create(0);

    // misc
    public static final ErrorCode INVALID_STRING = ErrorCode.create(101);
    public static final ErrorCode INVALID_USER = ErrorCode.create(102);
    public static final ErrorCode INVALID_NAME = ErrorCode.create(103);
    public static final ErrorCode INVALID_MAIL = ErrorCode.create(104);
    public static final ErrorCode INVALID_PASSWORD = ErrorCode.create(105);


    // register
    public static final ErrorCode NAME_VALIDATION_FAIL = ErrorCode.create(1001);
    public static final ErrorCode MAIL_VALIDATION_FAIL = ErrorCode.create(1002);
    public static final ErrorCode NAME_ALREADY_TAKEN = ErrorCode.create(1003);
    public static final ErrorCode MAIL_ALREADY_TAKEN = ErrorCode.create(1004);


}
