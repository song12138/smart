package org.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by paul on 2017/8/23.
 */
public class JsonUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();


    /**
     * POJO转JSON
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> String toJson(T obj) {
        String json = null;
        try {
            json = OBJECT_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     * JSON转POJO
     * @param json
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> T formJson(String json, Class<T> cls) {
        T pojo = null;
        try {
            OBJECT_MAPPER.readValue(json, cls);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pojo;
    }
}
