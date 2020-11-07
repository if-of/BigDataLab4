package com.donets.entity;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class BufferizedPageCreator {

    @Getter
    private Map<String, Page> buffer = new HashMap<>();

    public Page createPage(String fullUrl, String urlWithoutSchemaAndParameters) {
        Page page = buffer.get(fullUrl);
        if (page == null) {
            page = new Page(fullUrl, urlWithoutSchemaAndParameters);
            buffer.put(fullUrl, page);
        }
        return page;
    }
}
