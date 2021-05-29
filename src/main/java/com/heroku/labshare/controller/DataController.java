package com.heroku.labshare.controller;

import com.heroku.labshare.json.TaskJson;
import com.heroku.labshare.service.DataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.heroku.labshare.constant.SecurityConstants.TOKEN_PREFIX;

@RestController
@RequestMapping("/api/data")
@RequiredArgsConstructor
public class DataController {

    private final DataService dataService;

    @PostMapping(value = "/save", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public void save(@RequestPart("task") TaskJson taskJson, @RequestPart("file") MultipartFile file, @RequestHeader("authorization") String authorizationHeader) {
        String token = authorizationHeader.replace(TOKEN_PREFIX, "");
        dataService.saveTask(taskJson, file, token);
    }
}
