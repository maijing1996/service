//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.mj.utils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;

public final class JsonHelper {
    public static final ObjectMapper MAPPER = new ObjectMapper();

    private JsonHelper() {
        throw new UnsupportedOperationException("This is a helper class and cannot be instantiated");
    }

    public static <T> String toJson(T data) {
        try {
            return MAPPER.writeValueAsString(data);
        } catch (JsonProcessingException var2) {
            throw new RuntimeException("toJson failed.", var2);
        }
    }

    public static <T> T fromJson(String json, Class<T> tClass) {
        if (json != null && !"".equals(json)) {
            try {
                return MAPPER.readValue(json, tClass);
            } catch (IOException var3) {
                throw new RuntimeException("fromJson failed.", var3);
            }
        } else {
            return null;
        }
    }

    static {
        MAPPER.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        MAPPER.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        MAPPER.setSerializationInclusion(Include.NON_NULL);
    }
}
