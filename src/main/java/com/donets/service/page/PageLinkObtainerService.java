package com.donets.service.page;

import com.donets.entity.Page;
import com.donets.service.url.UrlExtractorService;
import com.donets.util.UrlUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PageLinkObtainerService {

    private final PageContentLoaderService pageContentLoaderService;
    private final UrlExtractorService linksExtractorService;

    public Set<Page> obtainPages(String startUrl, int maxPages) {
        String citeRootUrl = UrlUtils.getRootUrl(startUrl);
        Page startPage = new Page(startUrl, UrlUtils.trimParametersAndTrailingSlash(startUrl));

        Set<Page> processedPages = new HashSet<>();
        List<Page> pageForProcessing = new LinkedList<>();
        pageForProcessing.add(startPage);

        while (!pageForProcessing.isEmpty() && processedPages.size() < maxPages) {
            Page page = pageForProcessing.remove(0);
            processedPages.add(page);

            Set<Page> childrenPages = obtainChildrenPages(page, citeRootUrl);
            page.linkAllChildrenPages(childrenPages);
            addPageForProcessing(childrenPages, pageForProcessing, processedPages);
        }

        return processedPages;
    }

    private Set<Page> obtainChildrenPages(Page page, String citeRootUrl) {
        String content = pageContentLoaderService.loadPageContent(page.getFullUrl());
        Set<String> childrenRawUrls = linksExtractorService.extractUrls(content);

        return childrenRawUrls.stream()
                .map(url -> UrlUtils.getAbsoluteUrl(page.getFullUrl(), url))
                .filter(url -> UrlUtils.urlBelongsToRootUrl(url, citeRootUrl))
                .map(url -> new Page(url, UrlUtils.trimParametersAndTrailingSlash(url)))
                .collect(Collectors.toSet());
    }

    private void addPageForProcessing(Collection<Page> rawPage,
                                      Collection<Page> pageForProcessing,
                                      Collection<Page> processedPages) {
        for (Page page : rawPage) {
            if (!processedPages.contains(page)) {
                pageForProcessing.add(page);
            }
        }
    }
}
