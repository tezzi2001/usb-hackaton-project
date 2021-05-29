package com.heroku.labshare.service;

import com.heroku.labshare.json.SearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

@Service
@RequiredArgsConstructor
public class SearchService {

    public SearchResponse search() {
        return null;
    }

    public SearchResponse search(String input) {
        return null;
    }

    public SearchResponse search(String input, MultiValueMap<String, String> filters) {
        return null;
    }

    public SearchResponse search(MultiValueMap<String, String> filters) {
        return null;
    }
}
