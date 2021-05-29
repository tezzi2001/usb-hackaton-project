package com.heroku.labshare.service;

import com.heroku.labshare.model.Task;
import com.heroku.labshare.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public Task getTaskById(Long id) {
        return taskRepository.findById(id).get();
    }
}
