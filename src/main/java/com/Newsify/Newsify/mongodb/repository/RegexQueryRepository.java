package com.Newsify.Newsify.mongodb.repository;


import com.Newsify.Newsify.mongodb.model.RegexDTO;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RegexQueryRepository extends MongoRepository<RegexDTO, String> {
}
