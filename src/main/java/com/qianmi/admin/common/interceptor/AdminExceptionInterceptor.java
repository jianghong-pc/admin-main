package com.qianmi.admin.common.interceptor;

import com.qianmi.admin.common.constants.AppStatus;
import com.qianmi.admin.common.constants.ErrorCode;
import com.qianmi.admin.common.exception.AdminRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.MessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * A base interceptor for {@link AdminRuntimeException AdminRuntimeException}
 * <p>
 * <p>Filename: AdminExceptionInterceptor.java</p>
 * <p>Date: 2017-06-30 09:57.</p>
 *
 * @author  <a href="mailto:baixiaolin@baixiaolin.com">OF2510-白晓林</a>
 * @version 0.0.1
 * @see AdminRuntimeException
 */
@ConditionalOnWebApplication
@RestControllerAdvice(basePackages = "com.qianmi.admin")
@Order(Ordered.LOWEST_PRECEDENCE - 100)
@Slf4j
public class AdminExceptionInterceptor {

    private static final String DEFAULT_ERROR_CODE = ErrorCode.COMMON.UNKNOWN_EXCEPTION.getErrorCode();
    private static final String DEFAULT_ERROR_MESSAGE = ErrorCode.COMMON.UNKNOWN_EXCEPTION.getDescription();

    @Resource
    private MessageSource messageSource;

    /**
     * {@link AdminRuntimeException}异常拦截处理
     *
     * @param request 请求信息
     * @param ex      异常信息
     * @return 应答报文
     */
    @ExceptionHandler(value = AdminRuntimeException.class)
    public ResponseEntity<Map<String, Object>> adminExceptionHandler(final HttpServletRequest request, final AdminRuntimeException ex) {
        return buildExceptionResponse(request, ex);
    }

    /**
     * RuntimeException 异常拦截处理
     *
     * @param request 请求信息
     * @param ex      异常信息
     * @return 应答报文
     */
    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<Map<String, Object>> runtimeExceptionHandler(final HttpServletRequest request, final RuntimeException ex) {
        return buildExceptionResponse(request, ex);
    }

    private ResponseEntity<Map<String, Object>> buildExceptionResponse(final HttpServletRequest request, final Exception ex) {

        String errorCode = this.getErrorCodeFromAdminRuntimeException(ex);
        // 使用默认系统异常编码, 见:{@link BasicErrorCode#UNKNOWN_EXCEPTION 002540000000}
        errorCode = StringUtils.defaultIfBlank(errorCode, DEFAULT_ERROR_CODE);
        final Object[] args = this.getArgsFromAdminRuntimeException(ex);
        String defaultMessage = this.getErrorMessageFromAdminRuntimeException(ex);    // 优先采用 CentreException 的默认错误消息
        defaultMessage = StringUtils.defaultIfBlank(defaultMessage, errorCode); // 然后使用 errorCode 字段
        final String message = StringUtils.defaultString(messageSource.getMessage(errorCode, args, defaultMessage, request.getLocale()), DEFAULT_ERROR_MESSAGE);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("status", AppStatus.FAILED.getStatus());
        resultMap.put("message", message);

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(resultMap);
    }

    private String getErrorCodeFromAdminRuntimeException(final Exception ex) {
        if (ex instanceof AdminRuntimeException && StringUtils.isNotBlank(((AdminRuntimeException) ex).getErrorId())) {
            return ((AdminRuntimeException) ex).getErrorId();
        }
        return null;
    }

    private Object[] getArgsFromAdminRuntimeException(final Exception ex) {
        if (ex instanceof AdminRuntimeException && StringUtils.isNotBlank(((AdminRuntimeException) ex).getErrorId())) {
            return ((AdminRuntimeException) ex).getParams();
        }
        return null;
    }

    private String getErrorMessageFromAdminRuntimeException(final Exception ex) {
        if (ex instanceof AdminRuntimeException && StringUtils.isNotBlank(((AdminRuntimeException) ex).getErrorMsg())) {
            return ((AdminRuntimeException) ex).getErrorMsg();
        }
        if (Objects.nonNull(ex.getCause()) && ex.getCause() instanceof AdminRuntimeException && StringUtils.isNotBlank(((AdminRuntimeException) ex.getCause()).getErrorMsg())) {
            return ((AdminRuntimeException) ex.getCause()).getErrorMsg();
        }
        return ex.getMessage();
    }

}
