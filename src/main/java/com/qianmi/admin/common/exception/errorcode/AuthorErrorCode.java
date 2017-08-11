package com.qianmi.admin.common.exception.errorcode;

public abstract class AuthorErrorCode {

    public static final String GLOBAL = "0002";

    public static final String AUTHENTICATION = "0001";

    public static final String JWT_TOKEN_EXPIRED = "0002";

    public static final String JWT_SCOPES_IS_EMPTY = "0003";

    public static final String JWT_SIGNATURE_IS_ILLEGAL = "0004";

    public static final String METHOD_IS_SUPPORT = "0005";
}
