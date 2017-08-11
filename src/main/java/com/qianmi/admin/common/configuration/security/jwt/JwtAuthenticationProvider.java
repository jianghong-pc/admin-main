package com.qianmi.admin.common.configuration.security.jwt;

import com.qianmi.admin.bean.UserAuthenticationInfo;
import com.qianmi.admin.common.configuration.security.JwtSettingsProperties;
import com.qianmi.admin.common.configuration.security.jwt.model.token.RawAccessJwtToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class JwtAuthenticationProvider implements AuthenticationProvider {

    @Resource
    private JwtSettingsProperties jwtSettingsProperties;
    
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        RawAccessJwtToken rawAccessToken = (RawAccessJwtToken) authentication.getCredentials();

        Jws<Claims> jwsClaims = rawAccessToken.parseClaims(jwtSettingsProperties.getTokenSigningKey());
        String subject = jwsClaims.getBody().getSubject();

        @SuppressWarnings({"unchecked","rawtypes"})
        List<String> scopes = jwsClaims.getBody().get("scopes", List.class);

        //返回当前用户的权限
        List<GrantedAuthority> authorities = scopes.stream().map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        UserAuthenticationInfo context = UserAuthenticationInfo.create(subject, authorities);
        
        return new JwtAuthenticationToken(context, context.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
