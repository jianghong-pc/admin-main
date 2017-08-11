package com.qianmi.admin.bean;

import com.qianmi.admin.bean.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class GroupAuthority extends BaseEntity {

    private static final long serialVersionUID = 6564426284917169889L;

    /**
     * 用户组编号
     */
    private Integer groupId;

    /**
     * 权限列表
     */
    private List<Integer> authorityIds;

//
//    /**
//     * 获取角色名称
//     *
//     * @param roleList 角色列表
//     * @return 角色名称
//     */
//    public Set<String> getRoleNames(List<Group> roleList){
//       return roleList.stream().map(sysRole-> "ROLE_".concat(sysRole.getName())).collect(Collectors.toSet());
//    }

}
