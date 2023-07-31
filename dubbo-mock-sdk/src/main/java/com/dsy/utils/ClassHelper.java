package com.dsy.utils;


import com.dsy.constants.CommonConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * @Author: dongshuaiyin
 * @Description:
 * @Date: 2023/4/21 16:00
 * @Modified by:
 */
public class ClassHelper {

    private static final Logger logger = LoggerFactory.getLogger(ClassHelper.class);

    /**
     * 从完整类名中拿到简称，且首字母小写
     * @param completeName
     * @return
     */
    public static String getSimpleInterfaceName(String completeName) {
        return ConvertUtils.lowerCaseFirstLatter(ConvertUtils.splitReturnLast(completeName, CommonConst.SEPARATOR_POINT));
    }

    public static boolean isPrimitive(Class<?> type) {
        return type.isPrimitive()
                || type == String.class
                || type == Character.class
                || type == Boolean.class
                || type == Byte.class
                || type == Short.class
                || type == Integer.class
                || type == Long.class
                || type == Float.class
                || type == Double.class
                || type == Object.class;
    }

    public static boolean isBaseType(Class<?> type) {
        return type == String.class
                || type == Character.class
                || type == Boolean.class
                || type == Byte.class
                || type == Short.class
                || type == Integer.class
                || type == Long.class
                || type == Float.class
                || type == Double.class;
    }

    public static Type[] getReturnType(String interfaceName,String methodName,Class<?>[] parameterTypes){
        Class<?> cls = ReflectUtils.forName(interfaceName);
        Method method = null;
        try {
            method = cls.getMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException e) {
            logger.error("反射获取返回类型失败",e);
            return null;
        }
        return new Type[]{method.getReturnType(), method.getGenericReturnType()};
    }

    public static ClassLoader getClassLoader(){
        return getClassLoader(ClassHelper.class);
    }

    public static ClassLoader getClassLoader(Class<?> clazz) {
        ClassLoader cl = null;
        try {
            cl = Thread.currentThread().getContextClassLoader();
        } catch (Throwable ex) {
            // Cannot access thread context ClassLoader - falling back to system class loader...
        }
        if (cl == null) {
            // No thread context class loader -> use class loader of this class.
            cl = clazz.getClassLoader();
            if (cl == null) {
                // getClassLoader() returning null indicates the bootstrap ClassLoader
                try {
                    cl = ClassLoader.getSystemClassLoader();
                } catch (Throwable ex) {
                    // Cannot access system ClassLoader - oh well, maybe the caller can live with null...
                }
            }
        }

        return cl;
    }

}
