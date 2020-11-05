package com.donets.util;

import com.donets.entity.Page;

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

    private static void removeSelfLinks(Set<Page> pages) {
        for (Page page : pages) {
            page.getChildrenPages().remove(page);
        }
    }
}
