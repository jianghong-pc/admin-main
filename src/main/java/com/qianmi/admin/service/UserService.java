package com.qianmi.admin.service;

import com.qianmi.admin.bean.Group;
import com.qianmi.admin.bean.User;
import com.qianmi.admin.dao.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户相关操作服务
 */
@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    /**
     * 根据用户登录名获取基础用户信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    public User loadUserByName(String username) {
        return userMapper.loadUserByUserName(username);
    }

    public List<Group> listGroupsByUserId(Long userId) {
        return userMapper.listGroupsByUserId(userId);
    }
}
