package com.dsy.exception;

/**
 * @Author: dongshuaiyin
 * @Description: 错误枚举对象
 *
 * @Date: 2023/4/24 20:06
 * @Modified by:
 */
public enum ErrorEnum {

    ERROR("1", "系统错误"),

    /**
     * 5000 开头自定义异常
     */
    NO_SUPPORT_TYPE_EXCEPTION("5000", ""),
    MOCK_SERVER_REQUEST_EXCEPTION("5001", "调用mock-servicer获取mock失败"),
    NO_SET_MOCK_RESULT("5002", "当前接口暂未设置mock-result,请前往mock-serverf服务配置"),
    ;

    private String code;

    private String message;

    ErrorEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
