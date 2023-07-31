package com.dsy.exception;

/**
 * @Author: dongshuaiyin
 * @Description:
 * @Date: 2023/4/24 20:05
 * @Modified by:
 */
public class MockException extends RuntimeException {

    private ErrorEnum errorEnum;

    public MockException(ErrorEnum errorEnum) {
        super(errorEnum.getMessage());
    }
}
