package com.Newsify.Newsify.stream_data;

import com.Newsify.Newsify.mongodb.model.MediaStackArticle;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@Component
public class NewsAPIService {

    private final Logger LOGGER = LoggerFactory.getLogger(NewsAPIService.class);
    private final String API_KEY = "6d66c3af1918f211303a2ec4c3e497ec";
    private static final String NEWS_API_BASE_URL = "https://newsapi.org/v2/";
    private static final String MEDIA_STACK_BASE_URL = "http://api.mediastack.com/v1/news";
    private final RestTemplate restTemplate = new RestTemplate();

    public List<MediaStackArticle> fetchNewsData(boolean isPaid) {
        try {
            List<MediaStackArticle> allArticles = new ArrayList<>();
            int page = 1;
            int pageSize = 100;
            int totalResults = isPaid ? Integer.MAX_VALUE : 100;

            while (allArticles.size() < totalResults) {
                HttpHeaders headers = new HttpHeaders();
                headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

                UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(MEDIA_STACK_BASE_URL)
                        .queryParam("access_key", API_KEY)
                        .queryParam("limit", pageSize)
                        .queryParam("keywords", "india");

                HttpEntity<?> entity = new HttpEntity<>(headers);

                ResponseEntity<String> response = restTemplate.exchange(
                        builder.toUriString(),
                        HttpMethod.GET,
                        entity,
                        String.class);

                String jsonResponse = response.getBody();
                ObjectMapper objectMapper = new ObjectMapper();
                MediaStackResponse newsApiResponse = objectMapper.readValue(jsonResponse, MediaStackResponse.class);
                allArticles.addAll(newsApiResponse.getData());
                LOGGER.info("Total results fetched = " + allArticles);
                totalResults = isPaid ? newsApiResponse.getPagination().getTotal() : 100;
                page++;
            }
            return allArticles;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

