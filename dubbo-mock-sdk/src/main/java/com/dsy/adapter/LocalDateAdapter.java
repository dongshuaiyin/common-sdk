package com.dsy.adapter;

import com.google.gson.*;


import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;


/**
 * @Author: dongshuaiyin
 * @Description:
 * @Date: 2023/5/9 11:05
 * @Modified by:
 */
public class LocalDateAdapter implements JsonDeserializer<LocalDate> {

    private String patternStr;

    public LocalDateAdapter(String patternStr) {
        this.patternStr = patternStr;
    }

    @Override
    public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        DateTimeFormatter fmt = new DateTimeFormatterBuilder()
                .appendPattern(patternStr)
                .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
                .toFormatter();
        return LocalDate.parse(json.getAsString(), fmt);
    }
}
