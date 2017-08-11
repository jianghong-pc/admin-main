package com.qianmi.admin.common.configuration.security.auth;

import com.qianmi.admin.common.constants.Module;
import com.qianmi.admin.common.exception.AdminRuntimeException;
import com.qianmi.admin.common.exception.AuthMethodNotSupportedException;
import com.qianmi.admin.common.exception.JwtExpiredTokenException;
import com.qianmi.admin.common.exception.errorcode.AuthorErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class AjaxAwareAuthenticationFailureHandler implements AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException e) throws IOException, ServletException {
		
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);

		ObjectMapper mapper = new ObjectMapper();

		if (e instanceof BadCredentialsException) {
			mapper.writeValue(response.getWriter(), new AdminRuntimeException(Module.AUTH, AuthorErrorCode.AUTHENTICATION));
		} else if (e instanceof JwtExpiredTokenException) {
			mapper.writeValue(response.getWriter(), new AdminRuntimeException(Module.AUTH, AuthorErrorCode.JWT_TOKEN_EXPIRED));
		} else if (e instanceof AuthMethodNotSupportedException) {
			mapper.writeValue(response.getWriter(),new AdminRuntimeException(Module.AUTH, AuthorErrorCode.METHOD_IS_SUPPORT));
		}

		mapper.writeValue(response.getWriter(), new AdminRuntimeException(Module.AUTH, AuthorErrorCode.AUTHENTICATION));
	}
}
