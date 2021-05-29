package com.heroku.labshare.controller;

import static com.heroku.labshare.constant.SecurityConstants.TOKEN_PREFIX;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heroku.labshare.config.JsonConfig;
import com.heroku.labshare.json.SearchResponse;
import com.heroku.labshare.json.TaskJson;
import com.heroku.labshare.json.faculty.Faculty;
import com.heroku.labshare.json.specialty.Specialty;
import com.heroku.labshare.json.subject.Subject;
import com.heroku.labshare.json.wrapper.TaskIdWithUserIdWrapper;
import com.heroku.labshare.model.Task;
import com.heroku.labshare.service.DataService;

import com.heroku.labshare.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import lombok.SneakyThrows;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.heroku.labshare.constant.FiltersNames.getFiltersNames;
import static com.heroku.labshare.constant.SecurityConstants.TOKEN_PREFIX;

import com.heroku.labshare.service.TaskService;
import com.heroku.labshare.service.UserService;

@RestController
@RequestMapping("/api/data")
@RequiredArgsConstructor
public class DataController {

    private final DataService dataService;
    private final SearchService searchService;
    private final JsonConfig jsonConfig;
    private final ObjectMapper mapper;
    private final UserService userService;
    private final TaskService taskService;

    @PostMapping("/save")
    @SneakyThrows
    public void save(@RequestParam("task") String taskJsonAsString, @RequestParam("file") MultipartFile file,
                     @RequestHeader("authorization") String authorizationHeader) {
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

    @GetMapping("/downloadLink")
    public ResponseEntity<String> getDownloadLink(@RequestParam Long id) {
        String downloadLink = dataService.createDownloadLinkByTaskId(id);
        return new ResponseEntity<>(downloadLink, HttpStatus.OK);
    }

    @GetMapping("/search")
    public SearchResponse search(@RequestParam MultiValueMap<String, String> query) {
        List<String> inputAsList = query.remove("input");
        filterQuery(query);

        if (inputAsList == null || inputAsList.isEmpty()) {
            return query.isEmpty() ? searchService.search() : searchService.search(query);
        } else {
            String input = inputAsList.get(0);
            return query.isEmpty() ? searchService.search(input) : searchService.search(input, query);
        }
    }

    private void filterQuery(MultiValueMap<String, String> query) {
        Set<String> excludeKeySet = query.keySet().stream()
                .filter(key -> !getFiltersNames().contains(key))
                .collect(Collectors.toSet());
        excludeKeySet.forEach(query::remove);
      
    @PutMapping("/like/increase")
    public void likeTask(@RequestBody TaskIdWithUserIdWrapper wrapper) {
        userService.likeTask(wrapper.getUserId(), wrapper.getTaskId());
    }

    @GetMapping("/fetchTask")
    public Task fetchTask(@RequestParam Long id) {
        return taskService.getTaskById(id);
    }
}