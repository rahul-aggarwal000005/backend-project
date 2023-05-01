package com.Newsify.Newsify.stream_data;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Pagination {
    private Integer limit;
    private Integer offset;
    private Integer count;
    private Integer total;
}
