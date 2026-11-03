package com.example.apm.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class GoogleSonUtils {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    // 无符号日期格式
    // private static final DateTimeFormatter LONG_DATE_TIME_FORMATTER =
    // DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    // private static final DateTimeFormatter LONG_DATE_FORMATTER =
    // DateTimeFormatter.ofPattern("yyyyMMdd");
    // private static final DateTimeFormatter LONG_TIME_FORMATTER =
    // DateTimeFormatter.ofPattern("HHmmss");

    public interface GsonSerializerAdapter<T> extends JsonSerializer<T>, JsonDeserializer<T> {
        Class<T> rawClass();
    }

    public static class LocalDateTimeAdapter implements GsonSerializerAdapter<LocalDateTime> {
        // ------ LocalDateTime 序列化与反序列化 ------
        @Override
        public JsonElement serialize(LocalDateTime src, java.lang.reflect.Type typeOfSrc,
                JsonSerializationContext context) {
            return new JsonPrimitive(DATE_TIME_FORMATTER.format(src));
        }

        @Override
        public LocalDateTime deserialize(JsonElement json, java.lang.reflect.Type typeOfT,
                JsonDeserializationContext context)
                throws JsonParseException {
            return LocalDateTime.parse(json.getAsString(), DATE_TIME_FORMATTER);
        }

        @Override
        public Class<LocalDateTime> rawClass() {
            return LocalDateTime.class;
        }

    }

    public static class LocalDateAdapter implements GsonSerializerAdapter<LocalDate> {

        // // ------ LocalDate 序列化与反序列化 ------
        @Override
        public JsonElement serialize(LocalDate src, java.lang.reflect.Type typeOfSrc,
                JsonSerializationContext context) {
            return new JsonPrimitive(DATE_FORMATTER.format(src));
        }

        @Override
        public LocalDate deserialize(JsonElement json, java.lang.reflect.Type typeOfT,
                JsonDeserializationContext context)
                throws JsonParseException {
            return LocalDate.parse(json.getAsString(), DATE_FORMATTER);
        }

        public Class<LocalDate> rawClass() {
            return LocalDate.class;
        }

    }

    public static class LocalTimeAdapter implements GsonSerializerAdapter<LocalTime> {

        @Override
        public JsonElement serialize(LocalTime src, java.lang.reflect.Type typeOfSrc,
                JsonSerializationContext context) {
            return new JsonPrimitive(TIME_FORMATTER.format(src));
        }

        @Override
        public LocalTime deserialize(JsonElement json, java.lang.reflect.Type typeOfT,
                JsonDeserializationContext context)
                throws JsonParseException {
            return LocalTime.parse(json.getAsString(), TIME_FORMATTER);
        }

        public Class<LocalTime> rawClass() {
            return LocalTime.class;
        }

    }

    public static class GoogleSon {

        static final LocalDateTimeAdapter LOCAL_DATE_TIME_ADAPTER = new LocalDateTimeAdapter();
        static final LocalDateAdapter LOCAL_DATE_ADAPTER = new LocalDateAdapter();
        static final LocalTimeAdapter LOCAL_TIME_ADAPTER = new LocalTimeAdapter();

        final Gson DEFAULT;

        GoogleSon(String dateFormat) {
            DEFAULT = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, LOCAL_DATE_TIME_ADAPTER)
                    .registerTypeAdapter(LocalDate.class, LOCAL_DATE_ADAPTER)
                    .registerTypeAdapter(LocalTime.class, LOCAL_TIME_ADAPTER).setDateFormat(dateFormat).serializeNulls()
                    .disableHtmlEscaping().disableJdkUnsafe().create();
        }

        GoogleSon(String dateFormat, boolean showNullValue, boolean disableHtmlEscaping) {
            GsonBuilder builder = new GsonBuilder()
                    .registerTypeAdapter(LocalDateTime.class, LOCAL_DATE_TIME_ADAPTER)
                    .registerTypeAdapter(LocalDate.class, LOCAL_DATE_ADAPTER)
                    .registerTypeAdapter(LocalTime.class, LOCAL_TIME_ADAPTER);
            if (StringUtils.isNotBlank(dateFormat)) {
                builder.setDateFormat(dateFormat);// 日期格式
            }
            if (showNullValue) {
                builder.serializeNulls();// 序列化 null 值
            }
            if (disableHtmlEscaping) {
                builder.disableHtmlEscaping();// 禁用 HTML 转义
            }
            DEFAULT = builder.disableJdkUnsafe().create();
        }

        GoogleSon(String dateFormat, boolean showNullValue, boolean disableHtmlEscaping,
                GsonSerializerAdapter<?>... adapters) {
            GsonBuilder builder = new GsonBuilder()
                    .registerTypeAdapter(LocalDateTime.class, LOCAL_DATE_TIME_ADAPTER)
                    .registerTypeAdapter(LocalDate.class, LOCAL_DATE_ADAPTER)
                    .registerTypeAdapter(LocalTime.class, LOCAL_TIME_ADAPTER);
            for (GsonSerializerAdapter<?> adapter : adapters) {
                builder.registerTypeAdapter(adapter.rawClass(), adapter);
            }
            if (StringUtils.isNotBlank(dateFormat)) {
                builder.setDateFormat(dateFormat);// 日期格式
            }
            if (showNullValue) {
                builder.serializeNulls();// 序列化 null 值
            }
            if (disableHtmlEscaping) {
                builder.disableHtmlEscaping();// 禁用 HTML 转义
            }
            DEFAULT = builder.disableJdkUnsafe().create();
        }

        public String toJson(Object obj) {
            return DEFAULT.toJson(obj);
        }

        public <T> T fromJson(String json, Class<T> clazz) {
            return DEFAULT.fromJson(json, clazz);
        }

        public <T> T fromJson(String json, TypeToken<T> clazz) {
            return DEFAULT.fromJson(json, clazz);
        }

        public <T> List<T> fromJsonArray(String json, TypeToken<List<T>> typeOfT) {
            return DEFAULT.fromJson(json, typeOfT.getType());
        }

    }

    final static GoogleSon DEFAULT = new GoogleSon("yyyy-MM-dd HH:mm:ss");

    public static GoogleSon defaultGson() {
        return DEFAULT;
    }

    public static GoogleSon customerGson(String dateFormat, boolean showNullValue, boolean disableHtmlEscaping) {
        log.debug("GoogleSonUtils.customerGson time_formatter={},showNullValue={},disableHtmlEscaping={}", dateFormat,
                disableHtmlEscaping);
        return new GoogleSon(dateFormat, showNullValue, disableHtmlEscaping);
    }

    public static GoogleSon customerGson(boolean showNullValue, boolean disableHtmlEscaping) {
        log.debug("GoogleSonUtils.customerGson time_formatter={},showNullValue={},disableHtmlEscaping={}",
                "yyyy-MM-dd HH:mm:ss",
                disableHtmlEscaping);
        return new GoogleSon("yyyy-MM-dd HH:mm:ss", showNullValue, disableHtmlEscaping);
    }

    public static GoogleSon customerGson(String dateFormat, boolean showNullValue, boolean disableHtmlEscaping,
            GsonSerializerAdapter<?>... adapters) {
        log.debug("GoogleSonUtils.customerGsontime_formatter={},showNull Value={},disableHtmlEscaping={}", dateFormat,
                showNullValue, disableHtmlEscaping);
        return new GoogleSon(dateFormat, showNullValue, disableHtmlEscaping, adapters);
    }
}
