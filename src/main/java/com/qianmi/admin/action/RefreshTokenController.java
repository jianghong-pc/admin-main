package com.qianmi.admin.action;

import com.qianmi.admin.common.configuration.security.JwtSettingsProperties;
import com.qianmi.admin.common.configuration.security.jwt.model.token.AccessJwtToken;
import com.qianmi.admin.common.configuration.security.jwt.model.token.JwtTokenFactory;
import com.qianmi.admin.common.configuration.security.jwt.model.token.RawAccessJwtToken;
import com.qianmi.admin.common.configuration.security.jwt.model.token.RefreshToken;
import com.qianmi.admin.common.constants.JWTConstants;
import com.qianmi.admin.common.util.JwtTokenUtil;
import com.qianmi.admin.dao.mapper.SysRoleMapper;
import com.qianmi.admin.dao.mapper.SysUserMapper;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class RefreshTokenController {

    @Resource
    private JwtTokenFactory tokenFactory;

    @Resource
    private JwtSettingsProperties jwtSettingsProperties;

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private SysRoleMapper roleMapper;

    @RequestMapping(value="/api/auth/token", method= RequestMethod.GET, produces={ MediaType.APPLICATION_JSON_VALUE })
    public @ResponseBody
    AccessJwtToken refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String tokenPayload = JwtTokenUtil.getTokenPayload(request.getHeader(JWTConstants.JWT_TOKEN_HEADER_PARAM));
        
        RawAccessJwtToken rawToken = new RawAccessJwtToken(tokenPayload);
        RefreshToken refreshToken = RefreshToken.create(rawToken, jwtSettingsProperties.getTokenSigningKey());

//        String jti = refreshToken.getJti();
//        if (!tokenVerifier.verify(jti)) {
//            throw new InvalidJwtToken();
//        }

//        String subject = refreshToken.getSubject();
//        GroupAuthority sysUser = sysUserMapper.getUserByName(subject);
//
//        if(sysUser == null){
//            throw new UsernameNotFoundException("用户不存在: " + subject);
//        }
//
//        //用户的角色集合
//        List<Group> roleList = roleMapper.listByAdminUserId(sysUser.getId());
//
//        if (CollectionUtils.isEmpty(roleList)) throw new InsufficientAuthenticationException("User has no roles assigned");
//
//        List<GrantedAuthority> authorities = sysUser.getRoleNames(roleList).stream()
//                .map(SimpleGrantedAuthority::new)
//                .collect(Collectors.toList());
//
//
//        UserAuthenticationInfo userAuthenticationInfo = UserAuthenticationInfo.create(sysUser.getUsername(), authorities);
//
//        return tokenFactory.createAccessJwtToken(userAuthenticationInfo);
        return null;
    }
}
