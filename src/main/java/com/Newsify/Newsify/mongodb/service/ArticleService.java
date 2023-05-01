package com.Newsify.Newsify.mongodb.service;

import com.Newsify.Newsify.mongodb.model.MediaStackArticle;
import com.Newsify.Newsify.mongodb.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class ArticleService {
    @Autowired
    private ArticleRepository repository;
    public List<MediaStackArticle> getAllArticles() {
        return repository.findAll();
    }
    public MediaStackArticle saveArticle(MediaStackArticle article){
        return repository.save(article);
    }
    public MediaStackArticle getArticle(String id){
        return repository.findById(id).orElse(null);
    }
    public String deleteAllArticle(){
        repository.deleteAll();
        return "Successfully Delete all the articles";
    }
}
