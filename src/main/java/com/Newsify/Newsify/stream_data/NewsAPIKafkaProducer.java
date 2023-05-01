package com.Newsify.Newsify.stream_data;

import com.Newsify.Newsify.mongodb.model.MediaStackArticle;
import com.Newsify.Newsify.mongodb.service.ArticleService;
import com.Newsify.Newsify.redis.RedisService;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class NewsAPIKafkaProducer {

    private final ArticleService mongoArticleService;

    @Autowired
    private final RedisService redisService;
    private final String TOPIC_NAME = "news_topic";

    private final Logger LOGGER = LoggerFactory.getLogger(NewsAPIKafkaProducer.class);
    private final NewsAPIService newsAPIService;
    private final Set<Object> publishedArticlesUrl;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    /*public Set<String> getAlreadyPublishedArticlesUrl(List<MediaStackArticle> articles) {
        Set<String> urlsList = new HashSet<>();
        for (MediaStackArticle article : articles) {
            urlsList.add(article.getUrl());
        }
        return urlsList;
    }*/

    public NewsAPIKafkaProducer(NewsAPIService newsAPIService, ArticleService mongoArticleService, RedisService redisService) {
        this.redisService = redisService;
        this.mongoArticleService = mongoArticleService;
        this.publishedArticlesUrl = redisService.getAllNewsUrl();
        this.newsAPIService = newsAPIService;
    }

    public static String generateId(String url) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(url.getBytes(StandardCharsets.UTF_8));
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    @Scheduled(fixedDelay = 5 * 60 * 1000) // Fetch news data every 50 seconds
    public void fetchAndPublishNewsData() throws NoSuchAlgorithmException {
        List<MediaStackArticle> articles = newsAPIService.fetchNewsData(false);
//        List<MediaStackArticle> articles = List.of();
        for (MediaStackArticle article : articles) {
            if (!publishedArticlesUrl.contains(article.getUrl())) {
                // LOGGER.info("Publishing data to kafka with key : " + article.getTitle());
                article.setId(generateId(article.getUrl()));
                kafkaTemplate.send(new ProducerRecord<>(TOPIC_NAME, article.getId(), article.getDescription()));
                System.out.println(article.getId());
                mongoArticleService.saveArticle(article);
                redisService.addNewsUrl(article.getUrl());
                publishedArticlesUrl.add(article.getUrl());
            }
        }
    }
}