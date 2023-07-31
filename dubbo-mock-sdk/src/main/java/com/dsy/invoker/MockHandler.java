package com.dsy.invoker;

import com.alibaba.fastjson.JSON;
import com.dsy.bean.MockResultAdaptor;
import com.dsy.config.MockConfig;
import com.dsy.helper.RemoteRequestHelper;
import com.dsy.utils.ClassHelper;
import com.dsy.utils.MockValueResolver;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: dongshuaiyin
 * @Description:
 * @Date: 2023/4/21 16:05
 * @Modified by:
 */
@Slf4j
public class MockHandler {
    public static <T> Result invoke(Invoker<T> invoker, Invocation invocation, String interfaceName, String methodName) {
        String invokeInterfaceName = interfaceName;
        //mock总开关
        if (!MockConfig.INSTANCE.isMockEnable()) {
            return invoker.invoke(invocation);
        }
        //为开启接口mock 直接走正常逻辑
        if (MockConfig.INSTANCE.isSimpleInterfaceCheckEnable()) {
            invokeInterfaceName = ClassHelper.getSimpleInterfaceName(interfaceName);
        }
        if (MockConfig.INSTANCE.isInterfaceCheckEnable() && !MockConfig.INSTANCE.isMethodMockEnable(invokeInterfaceName, methodName) &&
                !MockConfig.INSTANCE.isInterfaceMockEnable(invokeInterfaceName)) {
            return invoker.invoke(invocation);
        }
        try {
            String mockValue = MockConfig.INSTANCE.getMockValue(invokeInterfaceName, methodName);
            if (MockConfig.INSTANCE.isRemoteMock()) {
                mockValue = RemoteRequestHelper.getRemoteMockValue(invokeInterfaceName, methodName);
            }
            //通过反射拿到返回的类型
            Type[] returnTypes = ClassHelper.getReturnType(interfaceName, methodName, invocation.getParameterTypes());
            Object returnValue = MockValueResolver.resolve(mockValue, returnTypes[0], returnTypes.length > 1 ? returnTypes[1] : null);
            log.info("mock 返回值:{}", JSON.toJSONString(returnValue));
            return new MockResultAdaptor() {
                @Override
                public Object getValue() {
                    return returnValue;
                }

                @Override
                public Object recreate() throws Throwable {
                    return returnValue;
                }

                @Override
                public Map<String, String> getAttachments() {
                    return new HashMap<>(0);
                }

                @Override
                public boolean hasException() {
                    return false;
                }
            };
        } catch (Exception e) {
            // TODO: 同样的异常处理
            log.error("interface:{} method:{}mock失败,转为正常调用", interfaceName, methodName, e);
        }
        //正常调用
        return invoker.invoke(invocation);
    }
}
