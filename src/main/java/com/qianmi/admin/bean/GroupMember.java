package com.qianmi.admin.bean;


import com.qianmi.admin.bean.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户与组关联关系
 *
 */
@Setter
@Getter
public class GroupMember extends BaseEntity {

    private static final long serialVersionUID = 2233003353250906959L;

    /**
     * 所属组编号
     */
    private Integer groupId;

    /**
     * 用户编号
     */
    private Long userId;

}
