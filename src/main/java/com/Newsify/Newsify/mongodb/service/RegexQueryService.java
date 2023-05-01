package com.Newsify.Newsify.mongodb.service;

import com.Newsify.Newsify.mongodb.model.RegexDTO;
import com.Newsify.Newsify.mongodb.repository.RegexQueryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegexQueryService {

    @Autowired
    private RegexQueryRepository regexQueryRepository;

    public RegexDTO saveRegex(RegexDTO tweet) {
        return regexQueryRepository.save(tweet);
    }

    public void deleteRegex(String id){
        regexQueryRepository.deleteById(id);
    }

    public List<RegexDTO> getAllRegex(){
        return regexQueryRepository.findAll();
    }
}
