package com.qianmi.admin.common.configuration.security.jwt.model;

public enum Scopes {

    REFRESH_TOKEN;
    
    public String authority() {
        return "ROLE_" + this.name();
    }
}
