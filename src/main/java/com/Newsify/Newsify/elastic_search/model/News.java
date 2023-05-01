package com.Newsify.Newsify.elastic_search.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "interested_news")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class News {
    @Id
    private String id;
    @Field(type = FieldType.Text, name = "description")
    private String description;
    @Field(type = FieldType.Text, name = "title")
    private String title;
    @Field(type = FieldType.Text, name = "url")
    private String url;
    @Field(type = FieldType.Text, name = "published_at")
    private String published_at;
}
