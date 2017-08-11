package com.qianmi.admin.dao.mapper;

import com.qianmi.admin.bean.Authority;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 系统用户权限操作，插入，删除，启/禁用
 *
 */
@Repository
public interface SysPermissionMapper {

    /**
     * 根据用户角色编号批量查询用户权限
     *
     * @param roleIds 角色编号集合
     * @return 权限列表
     */
    List<Authority> listByRoleIds(@Param("roleIds") List<Integer> roleIds);
}
