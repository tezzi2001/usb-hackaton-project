package com.heroku.labshare.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.heroku.labshare.json.filter.Filter;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SearchResponse {

    private Filter[] filters;
    @JsonProperty("items")
    private TaskJson[] tasks;
}
