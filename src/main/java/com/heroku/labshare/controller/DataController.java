package com.heroku.labshare.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.heroku.labshare.config.JsonConfig;
import com.heroku.labshare.json.faculty.Faculty;
import com.heroku.labshare.json.specialty.Specialty;
import com.heroku.labshare.json.subject.Subject;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/api/data")
@RequiredArgsConstructor
public class DataController {

    private final JsonConfig jsonConfig;

    @GetMapping("/faculty")
    public Faculty[] getFaculties() {
        return jsonConfig.getFaculties();
    }

    @GetMapping("/specialty")
    public Specialty[] getSpecialties() {
        return jsonConfig.getSpecialties();
    }

    @GetMapping("/subject")
    public Subject[] getSubjects() {
        return jsonConfig.getSubjects();
    }

}
