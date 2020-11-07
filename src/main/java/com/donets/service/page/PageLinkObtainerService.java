package com.donets.service.page;

import com.donets.entity.BufferizedPageCreator;
import com.donets.entity.Page;
import com.donets.service.url.UrlExtractorService;
import com.donets.util.UrlUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PageLinkObtainerService {

    private final PageContentLoaderService pageContentLoaderService;
    private final UrlExtractorService linksExtractorService;

    public Set<Page> obtainPages(String startUrl, int maxPages) {
        BufferizedPageCreator pageCreator = new BufferizedPageCreator();
        Set<Page> processedPages = new HashSet<>();
        Set<Page> pageForProcessing = new HashSet<>();

        String citeRootUrl = UrlUtils.getRootUrl(startUrl);
        Page startPage = pageCreator.createPage(
                startUrl,
                UrlUtils.trimParametersAndTrailingSlash(startUrl)
        );
        pageForProcessing.add(startPage);

        while (!pageForProcessing.isEmpty() && processedPages.size() < maxPages) {
            Iterator<Page> iterator = pageForProcessing.iterator();
            Page page = iterator.next();
            iterator.remove();
            processedPages.add(page);

            Set<Page> childrenPages = obtainChildrenPages(page, citeRootUrl, pageCreator);
            Page.linkPages(page, childrenPages);
            addPageForProcessing(childrenPages, pageForProcessing, processedPages);
        }

        return new HashSet<>(
                pageCreator.getBuffer().values()
        );
    }

    private Set<Page> obtainChildrenPages(Page page, String citeRootUrl, BufferizedPageCreator creator) {
        String content = pageContentLoaderService.loadPageContent(page.getFullUrl());
        Set<String> childrenRawUrls = linksExtractorService.extractUrls(content);

        return childrenRawUrls.stream()
                .map(url -> UrlUtils.getAbsoluteUrl(page.getFullUrl(), url))
                .filter(url -> UrlUtils.urlBelongsToRootUrl(url, citeRootUrl))
                .map(url -> creator.createPage(
                        url,
                        UrlUtils.trimParametersAndTrailingSlash(url)
                ))
                .collect(Collectors.toSet());
    }

    private void addPageForProcessing(Set<Page> childrenPages,
                                      Set<Page> pageForProcessing,
                                      Set<Page> processedPages) {
        for (Page page : childrenPages) {
            if (!processedPages.contains(page)) {
                pageForProcessing.add(page);
            }
        }
    }
}
