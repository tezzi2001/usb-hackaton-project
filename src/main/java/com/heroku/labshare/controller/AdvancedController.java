package com.heroku.labshare.controller;

import com.heroku.labshare.json.SearchResponse;
import com.heroku.labshare.service.AdvancedSearchService;
import com.heroku.labshare.service.async.AsyncFileCrawlerService;
import com.heroku.labshare.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.heroku.labshare.constant.FiltersNames.getFiltersNames;

@RestController
@RequestMapping("/api/data/advanced")
@RequiredArgsConstructor
public class AdvancedController {

    private final SearchService searchService;
    private final AdvancedSearchService advancedSearchService;
    private final AsyncFileCrawlerService asyncFileCrawlerService;

    @GetMapping("/search")
    public SearchResponse advancedSearch(@RequestParam MultiValueMap<String, String> query) {
        List<String> inputAsList = query.remove("input");
        filterQuery(query);

        if (inputAsList == null || inputAsList.isEmpty()) {
            return query.isEmpty() ? searchService.search() : searchService.search(query);
        } else {
            String input = inputAsList.get(0);
            return query.isEmpty() ? advancedSearchService.search(input) : advancedSearchService.search(input, query);
        }
    }

    @PostMapping("/crawler")
    public void runCrawler() {
        asyncFileCrawlerService.crawlerForcedRun();
    }

    //TODO: refactor (duplicate)
    private void filterQuery(MultiValueMap<String, String> query) {
        Set<String> excludeKeySet = query.keySet().stream()
                .filter(key -> !getFiltersNames().contains(key))
                .collect(Collectors.toSet());
        excludeKeySet.forEach(query::remove);
    }
}
