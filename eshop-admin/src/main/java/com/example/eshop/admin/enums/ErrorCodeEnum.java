package com.example.eshop.admin.enums;

public enum ErrorCodeEnum {
    SUCCESS(0, "执行成功"),

    /**
     * code:X0000相当于interface不具体不推荐使用，请使用有具体含义的子参数
     */
    PARAM_ERROR(10000, "参数错误"),
    MISSING_PARAM(10100, "缺少参数"),
    MISSING_PARAM_USERNAME(10101, "缺少用户名"),
    INVALID_PARAM(10200, "参数不合法"),
    INVALID_PARAM_USERNAME(10201, "用户名不合法"),

    ACCESS_DENIED(30000, "拒绝访问"),
    SERVICE_NOT_OPEN(30100, "服务未开放"),
    NOT_ALLOW_ACCESS(30200, "不允许访问"),
    NOT_LOGIN(30300, "用户未登录"),

    BIZ_ERROR(50000, "业务逻辑错误"),
    SERVER_ERROR(70000, "服务器错误"),

    EXCEPTION_ERROR(90700, "异常错误"),
    FAILURE(90900, "执行失败");

    private Integer code;
    private String message;

    ErrorCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() { return code; }

    public String getMessage() { return message; }
}