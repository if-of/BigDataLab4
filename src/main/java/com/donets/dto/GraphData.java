package com.donets.dto;

import lombok.Data;

import java.util.Set;

@Data
public class GraphData {

    private final Set<GraphNode> nodes;
    private final Set<GraphEdge> edges;
}
