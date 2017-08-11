package com.qianmi.admin.bean;


import com.qianmi.admin.bean.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Setter
@Getter
public class User extends BaseEntity {

    private static final long serialVersionUID = -8029875489712443626L;

    /**
     * 用户编号
     */
    private Long id;

    /**
     * 用户名称
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 状态 0：锁定 1：启用
     */
    private Integer status;

    /**
     * 添加时间
     */
    private Date addTime;

}