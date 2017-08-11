package com.qianmi.admin.bean;

import com.qianmi.admin.bean.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Group extends BaseEntity {

    /**
     * 角色编号
     */
    private Integer id;

    /**
     * 角色名称
     */
    private String name;

}
