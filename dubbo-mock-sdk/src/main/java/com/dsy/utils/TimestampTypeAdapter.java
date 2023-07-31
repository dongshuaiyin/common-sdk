package com.dsy.utils;

import com.google.gson.*;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: dongshuaiyin
 * @Description:
 * @Date: 2023/5/8 22:12
 * @Modified by:
 */
@Slf4j
public class TimestampTypeAdapter implements JsonDeserializer<Timestamp>, JsonSerializer<Timestamp> {

    private String formatStr;

    public TimestampTypeAdapter(String formatStr) {
        this.formatStr = formatStr;
    }

    @Override
    public Timestamp deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (!(json instanceof JsonPrimitive)) {
            throw new JsonParseException("The date should be a string value");
        }
        long timestamp;
        try {
            timestamp = Long.parseLong(json.getAsString());
        } catch (NumberFormatException numberFormatException) {
            log.warn("timestamp 转换long失败，继续执行时间类型转换.....");
            try {
                DateFormat format = new SimpleDateFormat(formatStr);
                Date date = format.parse(json.getAsString());
                return new Timestamp(date.getTime());
            } catch (ParseException e) {
                throw new JsonParseException(e);
            }
        }
        return new Timestamp(json.getAsLong());
    }

    @Override
    public JsonElement serialize(Timestamp src, Type typeOfSrc, JsonSerializationContext context) {
        String value = "";
        if(src != null){
            value = String.valueOf(src.getTime());
        }
        return new JsonPrimitive(value);
    }
}
