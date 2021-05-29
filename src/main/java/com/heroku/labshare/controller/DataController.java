package com.heroku.labshare.controller;

import com.heroku.labshare.json.TaskJson;
import com.heroku.labshare.service.DataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.heroku.labshare.config.JsonConfig;
import com.heroku.labshare.json.faculty.Faculty;
import com.heroku.labshare.json.specialty.Specialty;
import com.heroku.labshare.json.subject.Subject;

import static com.heroku.labshare.constant.SecurityConstants.TOKEN_PREFIX;

@RestController
@RequestMapping("/api/data")
@RequiredArgsConstructor
public class DataController {

    private final DataService dataService;
    private final JsonConfig jsonConfig;

    @PostMapping(value = "/save", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public void save(@RequestPart("task") TaskJson taskJson, @RequestPart("file") MultipartFile file, @RequestHeader("authorization") String authorizationHeader) {
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

    @GetMapping("/downloadLink")
    public ResponseEntity<String> getDownloadLink(@RequestParam Long id) {
        String downloadLink = dataService.createDownloadLinkByTaskId(id);
        return new ResponseEntity<>(downloadLink, HttpStatus.OK);
    }
}