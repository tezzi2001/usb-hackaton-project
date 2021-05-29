package com.heroku.labshare.json.filter;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Filter {

    private String id;
    private String name;
    private Option[] options;
}
