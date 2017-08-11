package com.qianmi.admin.common.constants;

/**
 * TODO
 * <p>Filename: AppStatus.java</p>
 * <p>Date: 2017-08-08 18:31.</p>
 *
 * @author <a href="mailto:jianghong@qianmi.com">of837-姜洪</a>
 * @since v0.0.1
 */
public enum AppStatus {
    SUCCESS(1),
    FAILED(-1),
    AUTH_FAILED(-2),
    SIGN_FAILED(-3),
    REQUEST_FAILED(-4),
    SERVER_FAILED(-5),
    VERSION_FORCE(-6);

    private Integer status;

    private AppStatus(int status) {
        this.status = status;
    }

    public Integer getStatus() {
        return this.status;
    }
}
