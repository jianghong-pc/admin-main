package com.qianmi.admin.bean.base;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 3786346180520124693L;

    /**
     * @return Bean的字符串描述；
     */
    @Override
    public final String toString() {
        return JSON.toJSONString(this);
    }
}