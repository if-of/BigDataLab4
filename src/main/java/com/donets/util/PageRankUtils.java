package com.donets.util;

import com.donets.dto.pagerank.PageRank;
import com.donets.entity.Page;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PageRankUtils {

    public static List<PageRank> convertSortAndLimit(Map<Page, Double> pageRank, int limit) {
        return pageRank.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(limit)
                .map(entry -> new PageRank(entry.getKey().getUrlWithoutSchemaAndParameters(), entry.getValue()))
                .collect(Collectors.toList());
    }
}
