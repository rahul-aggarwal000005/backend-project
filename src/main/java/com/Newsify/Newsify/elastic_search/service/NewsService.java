package com.Newsify.Newsify.elastic_search.service;

import com.Newsify.Newsify.elastic_search.model.News;
import com.Newsify.Newsify.elastic_search.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class NewsService {
    @Autowired
    private NewsRepository repository;

    public String save(News entity) throws IOException {
        return repository.createOrUpdateNews(entity);
    }

    public News findById(String id) throws IOException {
        return repository.getNewsById(id);
    }

    public void deleteById(String id) throws IOException {
        repository.deleteNewsById(id);
    }

    public List<News> findAllNews() throws IOException {
        return repository.getAllNews();
    }

    public String deleteAllNews() throws IOException {
        return repository.deleteAllNews();
    }

    public News getMostRecentInterestedNews() throws IOException {
        return repository.getMostRecentInterestedNews();
    }
}
