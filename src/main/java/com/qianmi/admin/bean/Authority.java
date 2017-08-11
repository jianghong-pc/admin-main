package com.qianmi.admin.bean;

import com.qianmi.admin.bean.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Authority extends BaseEntity {

    /**
     * 权限编号
     */
    private Integer id;

    /**
     * 权限名称
     */
    private String name;
}
