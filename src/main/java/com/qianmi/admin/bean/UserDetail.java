package com.qianmi.admin.bean;

import com.qianmi.admin.common.constants.Module;
import com.qianmi.admin.common.constants.UserStatus;
import com.qianmi.admin.common.exception.AdminRuntimeException;
import com.qianmi.admin.common.exception.errorcode.UserErrorCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * 用户详细信息
 * <p>Filename: com.qianmi.admin.bean.UserDetail.java</p>
 * <p>Date: 2017-08-10 16:22.</p>
 *
 * @author <a href="mailto:jianghong@qianmi.com">of837-姜洪</a>
 * @since v0.0.1
 */
public class UserDetail implements UserDetails {

    private User user;

    private Collection<? extends GrantedAuthority> authorities;

    public UserDetail(User user, Collection<? extends GrantedAuthority> authorities){
        this.user = user;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        if(user.getStatus().equals(UserStatus.ENABLED.getValue())){
            return true;
        }else if (user.getStatus().equals(UserStatus.DISABLED.getValue())){
            return false;
        }else {
            throw new AdminRuntimeException(Module.USER, UserErrorCode.USER_STATUS_NOT_EXISTS);
        }
    }
}
