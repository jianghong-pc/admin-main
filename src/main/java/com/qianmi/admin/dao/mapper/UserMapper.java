package com.qianmi.admin.dao.mapper;

import com.qianmi.admin.bean.Group;
import com.qianmi.admin.bean.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 基础用户信息操作，插入，删除，启/禁用
 */
@Repository
public interface UserMapper {

    /**
     * 根据用户名查询基础用户信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    User loadUserByUserName(@Param("username") String username);

    List<Group> listGroupsByUserId(@Param("userId") Long userId);
}
