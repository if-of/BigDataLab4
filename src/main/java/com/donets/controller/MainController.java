package com.donets.controller;

import com.donets.dto.RankAlgorithmInitDataDto;
import com.donets.entity.Page;
import com.donets.service.page.PageLinkObtainerService;
import com.donets.util.GraphUtils;
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
    public String greeting(@Valid @RequestBody RankAlgorithmInitDataDto rankAlgorithmInitDataDto) {
        Set<Page> pages = pageLinkObtainerService.obtainPages(
                rankAlgorithmInitDataDto.getUrl(),
                rankAlgorithmInitDataDto.getMaxPages()
        );

        GraphUtils.normalizePagesGraph(pages);


        return "{nodes:[],edges:[]}";
    }
}
