package com.donets.dto;

import com.donets.dto.graph.GraphData;
import com.donets.dto.pagerank.PageRank;
import lombok.Data;

import java.util.List;

@Data
public class Result {

    private final GraphData graphData;
    private final List<PageRank> pageRanks;
}
