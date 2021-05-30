package com.heroku.labshare.service;

import com.heroku.labshare.json.TaskJson;
import com.heroku.labshare.model.Task;
import com.heroku.labshare.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskJson getTaskById(Long id) {
        Task task = taskRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found by id " + id));
        return new TaskJson(task);
    }
}
