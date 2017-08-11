package com.qianmi.admin.common.configuration.security.jwt.model.token;

import com.qianmi.admin.common.constants.Module;
import com.qianmi.admin.common.exception.AdminRuntimeException;
import com.qianmi.admin.common.exception.errorcode.AuthorErrorCode;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;

public class RawAccessJwtToken {

    private static Logger logger = LoggerFactory.getLogger(RawAccessJwtToken.class);
            
    private String token;
    
    public RawAccessJwtToken(String token) {
        this.token = token;
    }

    /**
     * Parses and validates JWT Token signature.
     * 
     */
    public Jws<Claims> parseClaims(String signingKey) {
        try {
            return Jwts.parser().setSigningKey(signingKey).parseClaimsJws(this.token);
        } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException ex) {
            logger.error("Invalid JWT Token", ex);
            throw new BadCredentialsException("Invalid JWT token: ", ex);
        } catch (SignatureException signatureEx) {
            logger.info("signingKey is not validity", signatureEx);
            throw new AdminRuntimeException(Module.AUTH, AuthorErrorCode.JWT_SIGNATURE_IS_ILLEGAL);
        }catch (ExpiredJwtException expiredEx) {
            logger.info("JWT Token is expired", expiredEx);
            throw new AdminRuntimeException(Module.AUTH, AuthorErrorCode.JWT_TOKEN_EXPIRED);
        }
    }

    public String getToken() {
        return token;
    }
}
