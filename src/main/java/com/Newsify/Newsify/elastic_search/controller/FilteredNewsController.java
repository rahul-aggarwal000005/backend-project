package com.Newsify.Newsify.elastic_search.controller;

import com.Newsify.Newsify.elastic_search.service.NewsService;
import com.Newsify.Newsify.elastic_search.model.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/filter")
public class FilteredNewsController {

    @Autowired
    private NewsService service;
    @GetMapping
    public List<News> getAllNews() throws IOException {
        return service.findAllNews();
    }

    @GetMapping("/latest")
    public News latestNews() throws IOException {
        return service.getMostRecentInterestedNews();
    }

    @PostMapping
    public String saveNews(@RequestBody News news) throws IOException {
        return service.save(news);
    }

    @DeleteMapping
    public String deleteAllNews() throws IOException {
        return service.deleteAllNews();
    }

}
