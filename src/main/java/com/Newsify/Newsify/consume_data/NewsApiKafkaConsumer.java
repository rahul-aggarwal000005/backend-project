package com.Newsify.Newsify.consume_data;

import com.Newsify.Newsify.elastic_search.model.News;
import com.Newsify.Newsify.elastic_search.service.NewsService;
import com.Newsify.Newsify.mongodb.model.MediaStackArticle;
import com.Newsify.Newsify.mongodb.model.RegexDTO;
import com.Newsify.Newsify.mongodb.service.ArticleService;
import com.Newsify.Newsify.mongodb.service.RegexQueryService;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

@Component
public class NewsApiKafkaConsumer {
    private final KafkaConsumer<String, String> consumer;
    private final List<MediaStackArticle> articles;
    private final RegexQueryService regexQueryService;
    @Autowired
    private final ArticleService articleService;
    @Autowired
    private NewsService esNewsService;


    public NewsApiKafkaConsumer(ArticleService articleService, RegexQueryService regexQueryService, NewsService esNewsService) {
        this.regexQueryService = regexQueryService;
        this.articleService = articleService;
        this.esNewsService = esNewsService;
        // Set up consumer configuration
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "news_group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        // Create a Kafka consumer
        this.consumer = new KafkaConsumer<>(props);

        // Subscribe to the topic
        this.consumer.subscribe(Collections.singletonList("news_topic"));
        this.articles = articleService.getAllArticles();
    }

    @Scheduled(fixedDelay = 50000)
    public void consume() {
//         Poll for new messages
//        System.out.println("Consuming data ... ");
        ConsumerRecords<String, String> records = consumer.poll(100);
        for (ConsumerRecord<String, String> record : records) {
            String key = record.key();
            String value = record.value();
//            MediaStackArticle article = objectMapper.readValue(value, MediaStackArticle.class);
//            System.out.printf("key = %s, value = %s%n", key, value);
            process(key, value);
        }
//        for (MediaStackArticle article : articles) {
//            String id = article.getId();
//            String description = article.getDescription();
//            process(id, description);
//        }
    }

    public void process(String key, String description) {
        List<RegexDTO> allRegex = regexQueryService.getAllRegex();
        for (RegexDTO regexDTO : allRegex) {
            String regex = regexDTO.getRegex();
            boolean isMatchingRegex = matchRegex(regex, description);
            if (isMatchingRegex) {
                System.out.println("Matching : " + key);
                // save the matching news in the elastic search
                saveMatchingArticleInES(key);
            }
        }
    }

    public void saveMatchingArticleInES(String key){
        MediaStackArticle article = articleService.getArticle(key);
        News news = new News(key, article.getDescription(),article.getTitle(),article.getUrl(), article.getPublished_at());
        try {
            esNewsService.save(news);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean matchRegex(String regex, String content) {
        return content.matches(regex);
    }
}
