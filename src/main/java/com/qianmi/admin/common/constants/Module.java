package com.qianmi.admin.common.constants;

public enum Module {
    COMMON("000"),
    AUTH("001"),
    USER("002");

    private String moduleId;

    private Module(String moduleId) {
        this.moduleId = moduleId;
    }

    public String get() {
        return this.moduleId;
    }
}
