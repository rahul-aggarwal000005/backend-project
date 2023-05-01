package com.Newsify.Newsify.mongodb.controller;

import com.Newsify.Newsify.mongodb.model.MediaStackArticle;
import com.Newsify.Newsify.mongodb.repository.ArticleRepository;
import com.Newsify.Newsify.mongodb.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mongo/article")
public class ArticleController {

    @Autowired
    private ArticleService service;

    @GetMapping
    public List<MediaStackArticle> getAllArticles() {
        return service.getAllArticles();
    }

    @PostMapping
    public MediaStackArticle saveArticle(@RequestBody MediaStackArticle article) {
        return service.saveArticle(article);
    }

    @GetMapping("/{id}")
    public MediaStackArticle getArticle(@PathVariable("id") String id) {
        return service.getArticle(id);
    }

    @DeleteMapping
    public String deleteAllArticle() {
        return service.deleteAllArticle();
    }

}
