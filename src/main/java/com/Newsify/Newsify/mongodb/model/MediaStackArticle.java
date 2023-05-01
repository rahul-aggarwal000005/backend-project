package com.Newsify.Newsify.mongodb.model;

import com.mongodb.lang.Nullable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString
@Document(collection = "news-articles")
public class MediaStackArticle {
    @Id
    private String id;
    private String url;
    private String author;
    private String description;
    private String title;
    private String source;
    private String image;
    private String category;
    private String language;
    private String country;
    private String published_at;
}
