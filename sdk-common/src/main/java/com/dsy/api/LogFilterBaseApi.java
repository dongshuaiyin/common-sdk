package com.dsy.api;

/**
 * @InterfaceName: LogFilterBaseApi
 * @Description: 日志过滤基础服务
 * @Author: dongshuaiyin
 * @Date: 2022/8/17 18:48
 * @Version: 1.0
 **/
public interface LogFilterBaseApi {

    /**
     * 日志过滤校验标准， 默认反向校验，仅配置的不打印，其他的都打印
     * @return
     */
    default boolean checkStandard() {return false;};
}
