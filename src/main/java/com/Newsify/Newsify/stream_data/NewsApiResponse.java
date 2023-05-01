package com.Newsify.Newsify.stream_data;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class NewsApiResponse {

    private String status;
    private int totalResults;
    private String message;
    private List<Article> articles;

    @Getter
    @Setter
    @ToString
    public static class Article {

        private String title;
        private String author;
        private String description;
        private String url;
        private String urlToImage;
        private String publishedAt;
        private String content;

        private Source source;

    }
    @Getter
    @Setter
    @ToString
    public static class Source {
        private String id;
        private String name;
    }
}
