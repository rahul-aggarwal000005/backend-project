package com.Newsify.Newsify.mongodb.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

@Configuration
public class MongoDBConfig extends AbstractMongoClientConfiguration {

    @Override
    protected String getDatabaseName() {
        return "Newsify";
    }

    @Override
    public MongoClient mongoClient() {
        return MongoClients.create("mongodb+srv://admin:admin@cluster0.liadnqt.mongodb.net/?retryWrites=true&w=majority");
    }

}
