package com.Newsify.Newsify.stream_data;

import com.Newsify.Newsify.mongodb.model.MediaStackArticle;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;


@Getter
@Setter
@ToString
public class MediaStackResponse {
    private Pagination pagination;
    private List<MediaStackArticle> data;
}

