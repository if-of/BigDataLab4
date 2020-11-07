package com.donets.controller;

import com.donets.dto.RankAlgorithmInitData;
import com.donets.dto.Result;
import com.donets.dto.graph.GraphData;
import com.donets.dto.pagerank.PageRank;
import com.donets.entity.Page;
import com.donets.service.page.PageLinkObtainerService;
import com.donets.service.page.PageRankCalculatorService;
import com.donets.util.GraphUtils;
import com.donets.util.PageRankUtils;
import com.donets.util.PageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Validated
@RestController
@RequiredArgsConstructor
public class MainController {

    private final PageLinkObtainerService pageLinkObtainerService;
    private final PageRankCalculatorService pageRankCalculatorService;

    @PostMapping("/process")
    public Result greeting(@Valid @RequestBody RankAlgorithmInitData rankAlgorithmInitData) {
        Set<Page> pages = pageLinkObtainerService.obtainPages(
                rankAlgorithmInitData.getUrl(),
                rankAlgorithmInitData.getMaxPages() == 0
                        ? Integer.MAX_VALUE
                        : rankAlgorithmInitData.getMaxPages()
        );
        PageUtils.removeSelfLinks(pages);
        PageUtils.numberPagesGraph(pages);

        GraphData graphData = GraphUtils.createGraph(pages);
        Map<Page, Double> pageRanksMap = pageRankCalculatorService.calculatePageRanks(pages, 0.5);
        List<PageRank> pageRanks = PageRankUtils.convertSortAndLimit(pageRanksMap, 10);

        return new Result(graphData, pageRanks);
    }
}
