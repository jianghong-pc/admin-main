package com.qianmi.admin.common.configuration.security.auth;

import com.qianmi.admin.bean.Group;
import com.qianmi.admin.bean.User;
import com.qianmi.admin.bean.UserAuthenticationInfo;
import com.qianmi.admin.service.UserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class AjaxAuthenticationProvider implements AuthenticationProvider {

    @Resource
    private UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        Assert.notNull(authentication, "No authentication data provided");

        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        User user = userService.loadUserByName(username);

        if (user == null) {
            throw new UsernameNotFoundException("用户不存在: " + username);
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if (!encoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Authentication Failed. Username or Password not valid.");
        }

        //用户组集合
        List<Group> groupList = userService.listGroupsByUserId(user.getId());

        if (CollectionUtils.isEmpty(groupList))
            throw new InsufficientAuthenticationException("User has no roles assigned");

        List<GrantedAuthority> authorities = groupList.stream().map(group -> new SimpleGrantedAuthority(group.getName()))
                .collect(Collectors.toList());

        UserAuthenticationInfo userAuthenticationInfo = UserAuthenticationInfo.create(user.getUsername(), authorities);

        return new UsernamePasswordAuthenticationToken(userAuthenticationInfo, user.getPassword(), authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
//
//    public static void main(String[] args) {
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        System.out.println(encoder.encode("123456"));
//    }
}
