package com.donets.util;

import com.donets.dto.GraphData;
import com.donets.dto.GraphEdge;
import com.donets.dto.GraphNode;
import com.donets.entity.Page;

import java.util.HashSet;
import java.util.Set;

public class GraphUtils {

    public static GraphData createGraph(Set<Page> pages) {
        Set<GraphNode> graphNodes = new HashSet<>();
        for (Page page : pages) {
            graphNodes.add(new GraphNode(
                    page.getId(),
                    UrlUtils.getFileNameFromUrl(page.getFullUrl()),
                    page.getFullUrl()
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
