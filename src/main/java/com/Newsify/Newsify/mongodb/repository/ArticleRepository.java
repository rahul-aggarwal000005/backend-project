package com.Newsify.Newsify.mongodb.repository;

import com.Newsify.Newsify.mongodb.model.MediaStackArticle;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ArticleRepository extends MongoRepository<MediaStackArticle, String> {
}
