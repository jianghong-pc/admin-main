package com.qianmi.admin.common.constants;

import com.qianmi.admin.common.exception.AdminRuntimeException;
import com.qianmi.admin.common.interceptor.AdminExceptionInterceptor;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 错误编码静态常量维护
 * 对应的异常编码维护在: errorCode.properties 文件
 * <p>
 * <p>Filename: com.qianmi.pc.admin.bff.app.common.constants.ErrorCode.java</p>
 * <p>Date: 2017-06-30 18:15.</p>
 *
 * @author <a href="mailto:baixiaolin@baixiaolin.com">OF2510-白晓林</a>
 * @version 0.1.0
 * @see AdminRuntimeException (Throwable, String, Object...)
 * @see AdminExceptionInterceptor#buildExceptionResponse(HttpServletRequest, Exception)
 */
@RequiredArgsConstructor(staticName = "of", access = AccessLevel.PRIVATE)
public final class ErrorCode {

    private final Module module;
    private final String code;
    @Getter
    private final String description;

    public String getErrorCode() {
        return (Objects.nonNull(module) ? module.get() : Module.COMMON.get()) + code;
    }

    /**
     * 商品基础服务模块
     * 建议默认从0010开始，预留些给系统默认异常使用
     * 维护在: basicErrorCode.properties
     *
     * @see Module#COMMON
     */
    public interface COMMON {
        Module    MODULE            = Module.COMMON;
        ErrorCode UNKNOWN_EXCEPTION = ErrorCode.of(MODULE, "0000", "系统未知异常");
        ErrorCode PARAMETER_EXCEPTION = ErrorCode.of(MODULE, "0001", "参数异常");
    }
}
