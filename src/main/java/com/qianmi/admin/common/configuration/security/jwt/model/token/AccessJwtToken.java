package com.qianmi.admin.common.configuration.security.jwt.model.token;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.jsonwebtoken.Claims;
import lombok.Getter;

@Getter
public final class AccessJwtToken {

    private final String rawToken;

    @JsonIgnore
    private Claims claims;

    AccessJwtToken(final String token, Claims claims) {
        this.rawToken = token;
        this.claims = claims;
    }
}
