package com.heroku.labshare.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heroku.labshare.config.JsonConfig;
import com.heroku.labshare.json.TaskJson;
import com.heroku.labshare.json.faculty.Faculty;
import com.heroku.labshare.json.specialty.Specialty;
import com.heroku.labshare.json.subject.Subject;
import com.heroku.labshare.service.DataService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.heroku.labshare.constant.SecurityConstants.TOKEN_PREFIX;

@RestController
@RequestMapping("/api/data")
@RequiredArgsConstructor
public class DataController {

    private final DataService dataService;
    private final JsonConfig jsonConfig;
    private final ObjectMapper mapper;

    @PostMapping("/save")
    @SneakyThrows
    public void save(@RequestParam("task") String taskJsonAsString, @RequestParam("file") MultipartFile file, @RequestHeader("authorization") String authorizationHeader) {
        TaskJson taskJson = mapper.readValue(taskJsonAsString, TaskJson.class);
        String token = authorizationHeader.replace(TOKEN_PREFIX, "");
        dataService.saveTask(taskJson, file, token);
    }
  
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