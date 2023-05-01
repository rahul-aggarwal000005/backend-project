package com.Newsify.Newsify.stream_data;

import com.Newsify.Newsify.mongodb.model.MediaStackArticle;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;
import java.util.Map;

public class MediaStackArticleSerializer implements Serializer<MediaStackArticle> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // No configuration needed
    }

    @Override
    public byte[] serialize(String topic, MediaStackArticle data) {
        byte[] bytes = null;
        try {
            bytes = objectMapper.writeValueAsBytes(data);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    @Override
    public void close() {
        // No resources to close
    }
}