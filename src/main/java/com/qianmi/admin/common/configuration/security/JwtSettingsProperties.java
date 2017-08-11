package com.qianmi.admin.common.configuration.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "rest.security.jwt")
@Setter
@Getter
public class JwtSettingsProperties {

    private Integer tokenExpirationTime;

    private String tokenIssuer;

    private String tokenSigningKey;

    private Integer refreshTokenExpTime;

}
