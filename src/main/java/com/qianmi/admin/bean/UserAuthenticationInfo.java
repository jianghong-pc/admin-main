package com.qianmi.admin.bean;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Getter
public class UserAuthenticationInfo {

    private final String username;

    private final List<GrantedAuthority> authorities;

    private UserAuthenticationInfo(String username, List<GrantedAuthority> authorities) {
        this.username = username;
        this.authorities = authorities;
    }
    
    public static UserAuthenticationInfo create(String username, List<GrantedAuthority> authorities) {
        if (StringUtils.isBlank(username)) throw new IllegalArgumentException("Username is blank: " + username);
        return new UserAuthenticationInfo(username, authorities);
    }

}
