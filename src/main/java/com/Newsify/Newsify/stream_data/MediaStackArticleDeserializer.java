package com.Newsify.Newsify.stream_data;

import com.Newsify.Newsify.mongodb.model.MediaStackArticle;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import java.io.IOException;
import java.util.Map;
public class MediaStackArticleDeserializer implements Deserializer<MediaStackArticle> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // No configuration needed
    }

    @Override
    public MediaStackArticle deserialize(String topic, byte[] data) {
        MediaStackArticle response = null;
        try {
            response = objectMapper.readValue(data, MediaStackArticle.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public void close() {
        // No resources to close
    }
}