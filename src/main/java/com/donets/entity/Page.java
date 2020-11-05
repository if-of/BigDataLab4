package com.donets.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
public class Page {

    @Setter
    private int id = -1;
    private final String label;
    private final String fullUrl;
    private final String urlWithoutSchemaAndParameters;
    private final Set<Page> childrenPages;

    public Page(String fullUrl, String urlWithoutSchemaAndParameters, String label) {
        this.fullUrl = fullUrl;
        this.urlWithoutSchemaAndParameters = urlWithoutSchemaAndParameters;
        this.label = label;
        this.childrenPages = new HashSet<>();
    }

    public void linkAllChildrenPages(Collection<Page> childrenPages) {
        this.childrenPages.addAll(childrenPages);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Page page = (Page) o;
        return urlWithoutSchemaAndParameters.equals(page.urlWithoutSchemaAndParameters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(urlWithoutSchemaAndParameters);
    }
}
