package com.donets.entity;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class BufferizedPageCreator {

    @Getter
    private Map<String, Page> buffer = new HashMap<>();

    public Page createPage(String fullUrl, String urlWithoutParametersAndTrailingSlash) {
        Page page = buffer.get(urlWithoutParametersAndTrailingSlash);
        if (page == null) {
            page = new Page(fullUrl, urlWithoutParametersAndTrailingSlash);
            buffer.put(urlWithoutParametersAndTrailingSlash, page);
        }
        return page;
    }
}
