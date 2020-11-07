package com.donets.util;

import com.donets.dto.graph.GraphData;
import com.donets.dto.graph.GraphEdge;
import com.donets.dto.graph.GraphNode;
import com.donets.entity.Page;

import java.util.HashSet;
import java.util.Set;

public class GraphUtils {

    public static GraphData createGraph(Set<Page> pages) {
        Set<GraphNode> graphNodes = new HashSet<>();
        for (Page page : pages) {
            graphNodes.add(new GraphNode(
                    page.getId(),
                    UrlUtils.getFileNameFromUrl(page.getUrlWithoutSchemaAndParameters()),
                    page.getUrlWithoutSchemaAndParameters()
            ));
        }

        Set<GraphEdge> graphEdges = new HashSet<>();
        for (Page page : pages) {
            for (Page innerPages : page.getRefersTo()) {
                graphEdges.add(new GraphEdge(page.getId(), innerPages.getId()));
            }
        }

        return new GraphData(graphNodes, graphEdges);
    }
}
