package com.donets.controller;

import com.donets.dto.GraphData;
import com.donets.dto.RankAlgorithmInitData;
import com.donets.entity.Page;
import com.donets.service.page.PageLinkObtainerService;
import com.donets.util.GraphUtils;
import com.donets.util.PageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Set;

@Validated
@RestController
@RequiredArgsConstructor
public class MainController {

    private final PageLinkObtainerService pageLinkObtainerService;

    @PostMapping("/process")
    public GraphData greeting(@Valid @RequestBody RankAlgorithmInitData rankAlgorithmInitData) {
        Set<Page> pages = pageLinkObtainerService.obtainPages(
                rankAlgorithmInitData.getUrl(),
                rankAlgorithmInitData.getMaxPages() == 0
                        ? Integer.MAX_VALUE
                        : rankAlgorithmInitData.getMaxPages()
        );

        PageUtils.removeSelfLinks(pages);
        PageUtils.numberPagesGraph(pages);
        return GraphUtils.createGraph(pages);
    }
}
