package com.qianmi.admin.common.configuration.security.jwt.model.token;

import com.qianmi.admin.common.configuration.security.JwtSettingsProperties;
import com.qianmi.admin.common.configuration.security.jwt.model.Scopes;
import com.qianmi.admin.bean.UserAuthenticationInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.UUID;
import java.util.stream.Collectors;

@Configuration
public class JwtTokenFactory {

    @Resource
    private JwtSettingsProperties settings;

    public AccessJwtToken createAccessJwtToken(UserAuthenticationInfo userAuthenticationInfo) {

        if (StringUtils.isBlank(userAuthenticationInfo.getUsername()))
            throw new IllegalArgumentException("Cannot create JWT Token without username");

        if (userAuthenticationInfo.getAuthorities() == null || userAuthenticationInfo.getAuthorities().isEmpty())
            throw new IllegalArgumentException("User doesn't have any privileges");

        Claims claims = Jwts.claims().setSubject(userAuthenticationInfo.getUsername());
        claims.put("scopes", userAuthenticationInfo.getAuthorities().stream().map(Object::toString).collect(Collectors.toList()));

        DateTime currentTime = new DateTime();

        String token = Jwts.builder()
          .setClaims(claims)
          .setIssuer(settings.getTokenIssuer())
          .setIssuedAt(currentTime.toDate())
          .setExpiration(currentTime.plusMinutes(settings.getTokenExpirationTime()).toDate())
          .signWith(SignatureAlgorithm.HS512, settings.getTokenSigningKey())
        .compact();

        return new AccessJwtToken(token, claims);
    }

    public AccessJwtToken createRefreshToken(UserAuthenticationInfo userAuthenticationInfo) {
        if (StringUtils.isBlank(userAuthenticationInfo.getUsername())) {
            throw new IllegalArgumentException("Cannot create JWT Token without username");
        }

        DateTime currentTime = new DateTime();

        Claims claims = Jwts.claims().setSubject(userAuthenticationInfo.getUsername());
        claims.put("scopes", Collections.singletonList(Scopes.REFRESH_TOKEN.authority()));
        
        String token = Jwts.builder()
          .setClaims(claims)
          .setIssuer(settings.getTokenIssuer())
          .setId(UUID.randomUUID().toString())
          .setIssuedAt(currentTime.toDate())
          .setExpiration(currentTime.plusMinutes(settings.getRefreshTokenExpTime()).toDate())
          .signWith(SignatureAlgorithm.HS512, settings.getTokenSigningKey())
        .compact();

        return new AccessJwtToken(token, claims);
    }
}
