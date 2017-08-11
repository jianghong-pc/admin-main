package com.qianmi.admin.common.exception.errorcode;

/**
 * Enumeration of REST Error types.
 * 
 * @author vladimir.stankovic
 *
 *         Aug 3, 2016
 */
public abstract class BaseErrorCode {


    /**
     * 系统异常 00000000
     */
    public static final String SYSTEM_EXCEPTION = "0000";

    /**
     * 参数异常00000001
     */
    public static final String PARAMETER_EXCEPTION = "0002";

    /**
     * redis异常00000002
     */
    public static final String REDIS_EXCEPTION = "0008";


}
