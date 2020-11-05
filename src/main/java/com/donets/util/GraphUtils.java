package com.donets.util;

import com.donets.dto.GraphData;
import com.donets.dto.GraphEdge;
import com.donets.dto.GraphNode;
import com.donets.entity.Page;

import java.util.HashSet;
import java.util.Set;

public class GraphUtils {

    public static void normalizePagesGraph(Set<Page> pages) {
        removeSelfLinks(pages);

        for (Page page : pages) {
            for (Page page2 : pages) {
                if (page2.getChildrenPages().contains(page)) {
                    page2.getChildrenPages().remove(page);
                    page2.getChildrenPages().add(page);
                }
            }
        }
    }

    public static void numberPagesGraph(Set<Page> pages) {
        int lastId = 0;
        for (Page page : pages) {
            if (page.getId() < 0) {
                page.setId(lastId++);
            }
            for (Page innerPages : page.getChildrenPages()) {
                if (innerPages.getId() < 0) {
                    innerPages.setId(lastId++);
                }
            }
        }
    }

    public static GraphData createGraph(Set<Page> pages) {
        Set<GraphNode> graphNodes = new HashSet<>();
        for (Page page : pages) {
            graphNodes.add(new GraphNode(page.getId(), page.getLabel(), page.getFullUrl()));
            for (Page innerPages : page.getChildrenPages()) {
                graphNodes.add(new GraphNode(innerPages.getId(), innerPages.getLabel(), innerPages.getFullUrl()));
            }
        }

        Set<GraphEdge> graphEdges = new HashSet<>();
        for (Page page : pages) {
            for (Page innerPages : page.getChildrenPages()) {
                graphEdges.add(new GraphEdge(page.getId(), innerPages.getId()));
            }
        }

        return new GraphData(graphNodes, graphEdges);
    }

    private static void removeSelfLinks(Set<Page> pages) {
        for (Page page : pages) {
            page.getChildrenPages().remove(page);
        }
    }
}
