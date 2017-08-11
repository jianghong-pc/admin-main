package com.qianmi.admin.dao.mapper;

import com.qianmi.admin.bean.GroupAuthority;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 基础用户信息操作，插入，删除，启/禁用
 *
 */
@Repository
public interface SysUserMapper {

    /**
     * 根据用户名查询基础用户信息
     *
     * @param loginName 用户名
     * @return 用户信息
     */
    GroupAuthority getUserByName(@Param("loginName") String loginName);

}
