package com.Newsify.Newsify.mongodb.controller;

import com.Newsify.Newsify.mongodb.model.RegexDTO;
import com.Newsify.Newsify.mongodb.repository.RegexQueryRepository;
import com.Newsify.Newsify.mongodb.service.RegexQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/query")
public class RegexQueryController {

    @Autowired
    private RegexQueryService service;
    @PostMapping
    public ResponseEntity<?> saveRegex(@RequestBody RegexDTO regex) {
        RegexDTO savedRegex = service.saveRegex(regex);
        return ResponseEntity.ok(savedRegex);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRegex(@PathVariable("id") String id){
        service.deleteRegex(id);
        return ResponseEntity.ok("Successfully Deleted");
    }

    @GetMapping
    public ResponseEntity<?> getAllRegex(){
        List<RegexDTO> allRegex = service.getAllRegex();
        return ResponseEntity.ok(allRegex);
    }
}
