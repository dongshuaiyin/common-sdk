package com.dsy.helper;

import com.dsy.config.MockConfig;
import com.dsy.exception.ErrorEnum;
import com.dsy.exception.MockException;
import com.dsy.utils.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.util.ExceptionUtils;

/**
 * @Author: dongshuaiyin
 * @Description: 远程请求处理
 * @Date: 2023/4/24 20:00
 * @Modified by:
 */
@Slf4j
public class RemoteRequestHelper {

    /**
     * 获取远程 mock 参数
     * @param intefaceName 接口名称
     * @param methodName 方法名称
     * @return
     */
    public static String getRemoteMockValue(String intefaceName, String methodName) {
        String mockUrl = MockConfig.INSTANCE.getMockUrl(intefaceName, methodName);
        log.debug("触发remote-mock,Url=【{}】", mockUrl);
        String result = null;
        try {
            result = HttpUtil.doGet(mockUrl);
        } catch (Exception e) {
            log.error("mock-server fail..info={}", e);
            throw new MockException(ErrorEnum.MOCK_SERVER_REQUEST_EXCEPTION);
        }
        if ("".equals(result) || null == result) {
            throw new MockException(ErrorEnum.NO_SET_MOCK_RESULT);
        }
        return result;
    }
}
