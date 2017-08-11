package com.qianmi.admin.common.configuration.security.jwt.model.token;

import com.qianmi.admin.common.constants.Module;
import com.qianmi.admin.common.exception.AdminRuntimeException;
import com.qianmi.admin.common.exception.errorcode.AuthorErrorCode;
import com.qianmi.admin.common.configuration.security.jwt.model.Scopes;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class RefreshToken {

    private Jws<Claims> claims;

    private RefreshToken(Jws<Claims> claims) {
        this.claims = claims;
    }

    /**
     * Creates and validates Refresh token 
     * 
     * @param token
     * @param signingKey
     * 
     * @throws BadCredentialsException
     *
     * @return
     */
    public static RefreshToken create(RawAccessJwtToken token, String signingKey) {
        Jws<Claims> claims = token.parseClaims(signingKey);

        @SuppressWarnings({"unchecked","rawtypes"})
        List<String> scopes = claims.getBody().get("scopes", List.class);

        if(CollectionUtils.isEmpty(scopes)){
            throw new AdminRuntimeException(Module.AUTH, AuthorErrorCode.JWT_SCOPES_IS_EMPTY);
        }

        if (scopes.stream().noneMatch(scope -> Scopes.REFRESH_TOKEN.authority().equals(scope))) {
            throw new AdminRuntimeException(Module.AUTH, AuthorErrorCode.JWT_SCOPES_IS_EMPTY);
        }

        return new RefreshToken(claims);
    }

    public Jws<Claims> getClaims() {
        return claims;
    }
    
    public String getJti() {
        return claims.getBody().getId();
    }
    
    public String getSubject() {
        return claims.getBody().getSubject();
    }
}
