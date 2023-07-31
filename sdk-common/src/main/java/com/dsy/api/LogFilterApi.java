package com.dsy.api;

/**
 * @className: LogFilterApi
 * @Description: TODO
 * @Author: dongshuaiyin
 * @Date: 2022/8/17 18:08
 * @Version: 1.0
 **/
public interface LogFilterApi extends LogFilterBaseApi{

    /**
     * 需要
     * @return
     */
    boolean isNeedLog(String path);
}
