package com.heroku.labshare.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.heroku.labshare.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/like")
@RequiredArgsConstructor
public class LikeController {

    private final UserService userService;

    @PutMapping("/increase")
    public ResponseEntity<?> likeTask(@RequestParam Long userId, @RequestParam Long taskId) {
        userService.likeTask(userId, taskId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
