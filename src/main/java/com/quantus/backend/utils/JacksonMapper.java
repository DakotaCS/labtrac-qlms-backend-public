package com.quantus.backend.utils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import java.io.*;

/**
 * @author Peeyush Chandel
 * @author Dakota Soares
 * @version 2024.1
 * @since 2024-06-04
 */
public class JacksonMapper {

    private static ObjectMapper	objectMapper = new ObjectMapper();
    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setSerializationInclusion(Include.NON_NULL);
    }

    public static String getJsonStringFromObject(Object obj) throws JsonGenerationException,
            JsonMappingException, IOException {
        StringWriter writer = new StringWriter();
        objectMapper.writeValue(writer, obj);
        return writer.toString();
    }

    public static <T> T getJsonStringFromObject(String jsonString, Class<T> clazz) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectReader r = mapper.readerFor(clazz);
        return setReader(r).readValue(jsonString);
    }

    public static <T> T getObjectFromJsonString(Class<T> clazz, String requestJson,
                                                String callingMethod) throws IOException {
        T t = null;
        InputStream is = new ByteArrayInputStream(requestJson.getBytes("UTF-8"));
        t = (T) objectMapper.readValue(is, clazz);
        return t;
    }

    public static <T> T getObjectFromJsonString(String json, Class<T> className)
            throws JsonParseException, JsonMappingException, IOException {
        return getObjectFromJsonString(className, json, "null");
    }

    public static <T> Object getObjectFromJsonString(String json, TypeReference<T> typeRef)
            throws IOException {
        InputStream is = new ByteArrayInputStream(json.getBytes("UTF-8"));
        return objectMapper.readValue(is, typeRef);
    }

    public static ObjectReader setReader(final ObjectReader r) {
        return r.with(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)
                .without(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .without(DeserializationFeature.WRAP_EXCEPTIONS);
    }
}
