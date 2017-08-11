package com.qianmi.admin.common.exception;

import com.qianmi.admin.common.constants.Module;
import com.qianmi.admin.common.interceptor.AdminExceptionInterceptor;
import lombok.Getter;

/**
 * 商品bff控制台项目公共异常
 * <p>
 * <p>Filename: AdminRuntimeException.java</p>
 * <p>Date: 2017-06-30 09:40.</p>
 *
 * @author  <a href="mailto:baixiaolin@baixiaolin.com">OF2510-白晓林</a>
 * @version 0.0.1
 * @see AdminExceptionInterceptor
 */
@Getter
public class AdminRuntimeException extends RuntimeException {

    private static final long serialVersionUID = -2078021171600148009L;

    /**
     * 商品中心错误码
     */
    @Getter
    private String errorId;

    /**
     * 商品中心错误码对应的描述
     */
    @Getter
    private String errorMsg;

    @Getter
    private Object[] params;

    /**
     * 参数校验异常或不需要通过异常码读取异常信息时使用
     *
     * @param moduleId     模块号,3位
     * @param errorCode    商品中心4位错误码
     * @param errorMessage 错误信息
     */
    public AdminRuntimeException(Module moduleId,
                                 String errorCode,
                                 String errorMessage,
                                 Object... params) {
        super(errorMessage);

        this.errorId = getFullErrorCode(moduleId, errorCode);
        this.errorMsg = errorMessage;
        this.params = params;
    }

    /**
     * 需要通过异常码读取异常信息时使用
     *
     * @param fullErrorCode 商品中心4位错误码
     */
    public AdminRuntimeException(String fullErrorCode) {
        super(fullErrorCode);
        this.errorId = fullErrorCode;
    }

    /**
     * 需要通过异常码读取异常信息时使用
     *
     * @param moduleId  模块号,3位
     * @param errorCode 商品中心4位错误码
     */
    public AdminRuntimeException(Module moduleId, String errorCode) {
        super(getFullErrorCode(moduleId, errorCode));

        this.errorId = getFullErrorCode(moduleId, errorCode);
    }

    private static String getFullErrorCode(Module moduleId, String errorCode) {
        return moduleId.get() + errorCode;
    }

    public String getMessage() {
        if (errorMsg == null || errorMsg.length() == 0) {
            return super.getMessage();
        }

        return errorMsg;
    }

    public String toString() {
        return errorId + ":" + errorMsg;
    }
}
