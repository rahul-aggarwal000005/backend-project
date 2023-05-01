package com.Newsify.Newsify.elastic_search.repository;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.Newsify.Newsify.elastic_search.model.News;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class NewsRepository {

    @Autowired
    private ElasticsearchClient elasticsearchClient;

    private final String indexName = "filtered_news";

    public String createOrUpdateNews(News news) throws IOException {

        IndexResponse response = elasticsearchClient.index(i -> i.index(indexName)
                        .id(news.getId())
                        .document(news)
        );
        if(response.result().name().equals("Created")){
            return new StringBuilder("Invoice document has been created successfully.").toString();
        }else if(response.result().name().equals("Updated")){
            return new StringBuilder("Invoice document has been updated successfully.").toString();
        }
        return new StringBuilder("Error while performing the operation.").toString();
    }

    public News getNewsById(String newsId) throws IOException{
        News news = null;
        GetResponse<News> response = elasticsearchClient.get(
                g -> g.index(indexName)
                        .id(newsId),
                News.class
        );

        if (response.found()) {
            news = response.source();
            assert news != null;
            System.out.println("Invoice name is: " + news.getId());
        } else {
            System.out.println ("Invoice not found");
        }
        return news;
    }

    public String deleteNewsById(String newsId) throws IOException {

        DeleteRequest request = DeleteRequest.of(d -> d.index(indexName).id(newsId));

        DeleteResponse deleteResponse = elasticsearchClient.delete(request);
        if (Objects.nonNull(deleteResponse.result()) && !deleteResponse.result().name().equals("NotFound")) {
            return new StringBuilder("Invoice with id : " + deleteResponse.id()+ " has been deleted successfully !.").toString();
        }
        System.out.println("Invoice not found");
        return new StringBuilder("Invoice with id : " + deleteResponse.id()+" does not exist.").toString();
    }

    public List<News> getAllNews() throws IOException {

        SearchRequest searchRequest = SearchRequest.of(s -> s.index(indexName));
        SearchResponse<News> searchResponse = elasticsearchClient.search(searchRequest, News.class);
        List<Hit<News>> hits = searchResponse.hits().hits();
        List<News> newsList = new ArrayList<>();
        for(Hit<News> object : hits){
            System.out.print(((News) object.source()));
            newsList.add((News) object.source());
        }
        return newsList;
    }

    public String deleteAllNews() throws IOException {
        List<News> newsList = getAllNews();
        for(News news: newsList){
            deleteNewsById(news.getId());
        }
        return "Successfully Deleted all news";
    }
}