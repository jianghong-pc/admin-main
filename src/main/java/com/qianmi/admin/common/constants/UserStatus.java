package com.qianmi.admin.common.constants;

/**
 * TODO
 * <p>Filename: com.qianmi.admin.common.constants.UserStatus.java</p>
 * <p>Date: 2017-08-10 16:51.</p>
 *
 * @author <a href="mailto:jianghong@qianmi.com">of837-姜洪</a>
 * @since v0.0.1
 */
public enum UserStatus {

    ENABLED(1),
    DISABLED(0);

    private int value;

    UserStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
