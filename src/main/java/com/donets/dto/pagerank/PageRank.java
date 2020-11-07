package com.donets.dto.pagerank;

import lombok.Data;

@Data
public class PageRank {

    private final String url;
    private final double score;
}
