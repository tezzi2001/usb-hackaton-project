package com.heroku.labshare.json.subject;

import lombok.Data;

@Data
public class Subject {

    private Integer id;
    private String name;
    private Integer[] specialtyId;
}
