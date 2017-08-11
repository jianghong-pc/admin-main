package com.qianmi.admin.common.configuration.security.auth;

import com.qianmi.admin.bean.UserAuthenticationInfo;
import com.qianmi.admin.common.configuration.security.jwt.model.token.AccessJwtToken;
import com.qianmi.admin.common.configuration.security.jwt.model.token.JwtTokenFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class AjaxAwareAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Resource
    private JwtTokenFactory tokenFactory;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        UserAuthenticationInfo userAuthenticationInfo = (UserAuthenticationInfo) authentication.getPrincipal();
        
        AccessJwtToken accessToken = tokenFactory.createAccessJwtToken(userAuthenticationInfo);
        AccessJwtToken refreshToken = tokenFactory.createRefreshToken(userAuthenticationInfo);
        
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", accessToken.getRawToken());
        tokenMap.put("refreshToken", refreshToken.getRawToken());

        ObjectMapper mapper = new ObjectMapper();

        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        mapper.writeValue(response.getWriter(), tokenMap);

        clearAuthenticationAttributes(request);
    }

    /**
     * Removes temporary authentication-related data which may have been stored
     * in the session during the authentication process..
     * 
     */
    private void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null) {
            return;
        }

        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
}
