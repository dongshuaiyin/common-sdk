package com.dsy.config;

import com.dsy.constants.CommonConst;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @Author: dongshuaiyin
 * @Description:
 * @Date: 2023/4/21 16:00
 * @Modified by:
 */
@Slf4j
public class MockConfig {

    public static final MockConfig INSTANCE = new MockConfig();

    private static final String REQUEST_URL_FORMAT = "/mock/getMockResult";

    @Getter
    @Setter
    private String urlPrefix;

    @Getter
    @Setter
    private Boolean enable;

    @Getter
    @Setter
    private Boolean enableInterfaceCheck;

    @Getter
    @Setter
    private Boolean enableSimpleInterfaceCheck;

    @Getter
    @Setter
    private Boolean enableRemoteMock;

    @Getter
    @Setter
    private Map<String,Boolean> interfaceMockConfig = new HashMap<>();

    /**
     *  mockvalue key=interfaceName + "." + methodName  value= mockValue
     */
    @Getter
    @Setter
    private Map<String, String> mockValue = new HashMap<>();

    private MockConfig() {
        this.enable = true;
    }

    public Boolean isMockEnable(){
        return Optional.ofNullable(enable).orElse(false);
    }

    /**
     * 是否开启接口检查
     * @return
     */
    public Boolean isInterfaceCheckEnable(){
        return Optional.ofNullable(enableInterfaceCheck).orElse(false);
    }

    /**
     * 简单接口检查
     * @return
     */
    public Boolean isSimpleInterfaceCheckEnable(){
        return Optional.ofNullable(enableSimpleInterfaceCheck).orElse(false);
    }

    public Boolean isInterfaceMockEnable(String interfaceName){
        return Optional.ofNullable(interfaceMockConfig.get(interfaceName)).orElse(false);
    }

    public Boolean isMethodMockEnable(String interfaceName, String methodName){
        return Optional.ofNullable(interfaceMockConfig.get(interfaceName+"."+methodName)).orElse(false);
    }

    /**
     * 是否开启远程调用
     * @return
     */
    public Boolean isRemoteMock(){
        return Optional.ofNullable(enableRemoteMock).orElse(false);
    }

    public String getLocalMockValue(String interfaceName, String methodName){
        return Optional.ofNullable(mockValue.get(interfaceName+"."+methodName)).orElse("");
    }

    public String getMockUrl(String interfaceName, String methodName) {
        String mockUrlPrefix = getMockUrlPrefix();
        return String.format(mockUrlPrefix + "/%s/%s", interfaceName, methodName);
    }

    /**
     * 拿到配置的value
     * @param interfaceName
     * @param methodName
     * @return
     */
    public String getMockValue(String interfaceName, String methodName) {
        String key = interfaceName + CommonConst.SEPARATOR_POINT + methodName;
        return Optional.ofNullable(mockValue.get(key)).orElse("");
    }

    private String getMockUrlPrefix(){
        return this.urlPrefix + REQUEST_URL_FORMAT;
    }

}
