package com.Newsify.Newsify.mongodb.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "newsify-regex")
@NoArgsConstructor
@Getter
@Setter
public class RegexDTO {
    @Id
    private String id;

    private String regex;

    public RegexDTO(String regex) {
        this.regex = regex;
    }
}
