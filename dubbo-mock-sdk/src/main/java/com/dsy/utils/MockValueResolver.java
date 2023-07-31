package com.dsy.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.dsy.adapter.LocalDateAdapter;
import com.dsy.constants.CommonConst;
import com.dsy.exception.ErrorEnum;
import com.dsy.exception.MockException;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Objects;

/**
 * @Author: dongshuaiyin
 * @Description:
 * @Date: 2023/4/21 16:00
 * @Modified by:
 */
public class MockValueResolver {

    private static final String TIMSTAMP = "java.sql.Timestamp";
    private static final String LOCALDATE = "java.time.LocalDate";

    public static <T> Object resolve(String mockValue, Type type,Type genericType) throws ClassNotFoundException {
        if (Void.TYPE.isAssignableFrom((Class<?>) type)) {
            return null;
        }
        //TODO 这个位置还需要优化。
        if (mockValue.startsWith("{")) {
            if (genericType == null) {
                return JSON.parseObject(mockValue, type);
            }
            //存在泛型，并且指定泛型的情况
            if (genericType.getTypeName().contains(CommonConst.SEPARATOR_LEFT_BOOK_TITLE) && genericType.getTypeName().contains(CommonConst.SEPARATOR_RIGHT_BOOK_TITLE)) {
                Gson gson = buildGson(genericType);
                //序列化
                return gson.fromJson(mockValue, genericType);
            } else {
                //存在泛型，但是没有指定的情况，默认走对型转换
                return JSON.parseObject(mockValue, type);
            }
        }
        throw new MockException(ErrorEnum.NO_SUPPORT_TYPE_EXCEPTION);
    }

    private static Gson buildGson(Type genericType) throws ClassNotFoundException {
        String dateFormat = isDateFormat(genericType);
        String isTimestamp = isTimestamp(genericType);
        String isLocaDate = isLocaDate(genericType);
        GsonBuilder gsonBuilder = new GsonBuilder();
        if (!"".equals(isLocaDate)) {
            gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateAdapter(isLocaDate));
        }
        if (!"".equals(dateFormat)) {
            gsonBuilder.setDateFormat(dateFormat);
        }
        if (!"".equals(isTimestamp)) {
            gsonBuilder.registerTypeAdapter(Timestamp.class, new TimestampTypeAdapter(isTimestamp));
        }
        return gsonBuilder.create();
    }

    private static String isTimestamp(Type genericType) throws ClassNotFoundException {
        String typeName = ((ParameterizedTypeImpl) genericType).getActualTypeArguments()[0].getTypeName();
        if (!typeName.contains(CommonConst.SEPARATOR_LEFT_BOOK_TITLE)) {
            return "";
        }
        int left = typeName.indexOf(CommonConst.SEPARATOR_LEFT_BOOK_TITLE) + 1;
        int right = typeName.indexOf(CommonConst.SEPARATOR_RIGHT_BOOK_TITLE);
        String className = typeName.substring(left, right);
        if (!className.contains(CommonConst.SEPARATOR_COMMA)) {
            Class<?> clazz = Class.forName(className.trim());
            Field[] declaredFields = clazz.getDeclaredFields();
            for (Field field : declaredFields) {
                String name = field.getType().getName();
                JsonFormat annotation = field.getAnnotation(JsonFormat.class);
                if (name.equals(TIMSTAMP) && !Objects.isNull(annotation)) {
                    return annotation.pattern();
                }
            }
            return "";
        }
        String[] splits = className.split(CommonConst.SEPARATOR_COMMA);
        for (String split : splits) {
            Class<?> clazz = Class.forName(split.trim());
            if (ClassHelper.isBaseType(clazz)) {
                continue;
            }
            Field[] declaredFields = clazz.getDeclaredFields();
            for (Field field : declaredFields) {
                String name = field.getType().getName();
                JsonFormat annotation = field.getAnnotation(JsonFormat.class);
                if (name.equals(TIMSTAMP) && !Objects.isNull(annotation)) {
                    return annotation.pattern();
                }
            }
        }
        return "";
    }

    private static String isLocaDate(Type genericType) throws ClassNotFoundException {
        String typeName = ((ParameterizedTypeImpl) genericType).getActualTypeArguments()[0].getTypeName();
        if (!typeName.contains(CommonConst.SEPARATOR_LEFT_BOOK_TITLE)) {
            return "";
        }
        int left = typeName.indexOf(CommonConst.SEPARATOR_LEFT_BOOK_TITLE) + 1;
        int right = typeName.indexOf(CommonConst.SEPARATOR_RIGHT_BOOK_TITLE);
        String className = typeName.substring(left, right);
        if (!className.contains(CommonConst.SEPARATOR_COMMA)) {
            Class<?> clazz = Class.forName(className.trim());
            Field[] declaredFields = clazz.getDeclaredFields();
            for (Field field : declaredFields) {
                String name = field.getType().getName();
                JsonFormat annotation = field.getAnnotation(JsonFormat.class);
                if (name.equals(LOCALDATE) && !Objects.isNull(annotation)) {
                    return annotation.pattern();
                }
            }
            return "";
        }
        String[] splits = className.split(CommonConst.SEPARATOR_COMMA);
        for (String split : splits) {
            Class<?> clazz = Class.forName(split.trim());
            if (ClassHelper.isBaseType(clazz)) {
                continue;
            }
            Field[] declaredFields = clazz.getDeclaredFields();
            for (Field field : declaredFields) {
                String name = field.getType().getName();
                JsonFormat annotation = field.getAnnotation(JsonFormat.class);
                if (name.equals(LOCALDATE) && !Objects.isNull(annotation)) {
                    return annotation.pattern();
                }
            }
        }
        return "";
    }

    private static String isDateFormat(Type genericType) throws ClassNotFoundException {
        String typeName = ((ParameterizedTypeImpl) genericType).getActualTypeArguments()[0].getTypeName();
        if (!typeName.contains(CommonConst.SEPARATOR_LEFT_BOOK_TITLE)) {
            return "";
        }
        int left = typeName.indexOf(CommonConst.SEPARATOR_LEFT_BOOK_TITLE) + 1;
        int right = typeName.indexOf(CommonConst.SEPARATOR_RIGHT_BOOK_TITLE);
        String className = typeName.substring(left, right);
        if (!className.contains(CommonConst.SEPARATOR_COMMA)) {
            Class<?> clazz = Class.forName(className.trim());
            Field[] declaredFields = clazz.getDeclaredFields();
            for (Field field : declaredFields) {
                JsonFormat annotation = field.getAnnotation(JsonFormat.class);
                if (!Objects.isNull(annotation)) {
                    return annotation.pattern();
                }
            }
            return "";
        }
        String[] splits = className.split(CommonConst.SEPARATOR_COMMA);
        for (String split : splits) {
            Class<?> clazz = Class.forName(split.trim());
            if (ClassHelper.isBaseType(clazz)) {
                continue;
            }
            Field[] declaredFields = clazz.getDeclaredFields();
            for (Field field : declaredFields) {
                JsonFormat annotation = field.getAnnotation(JsonFormat.class);
                if (!Objects.isNull(annotation)) {
                    return annotation.pattern();
                }
            }
        }
        return "";
    }

}
