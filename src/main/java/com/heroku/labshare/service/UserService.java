package com.heroku.labshare.service;

import java.util.Arrays;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auth0.jwt.JWT;
import com.heroku.labshare.json.UserJson;
import com.heroku.labshare.model.Task;
import com.heroku.labshare.model.User;
import com.heroku.labshare.repository.TaskRepository;
import com.heroku.labshare.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public UserJson getUserInfo(String token) {
        String username = JWT.decode(token).getSubject();
        return new UserJson(userRepository.findByUsername(username).orElseThrow());
    }

    @SneakyThrows
    @Transactional
    public void likeTask(Long userId, Long taskId) {
        User currentUser = userRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("User not found by id " + userId));

        Task likedTask = taskRepository.findById(taskId)
            .orElseThrow(() -> new EntityNotFoundException("Task not found by id " + userId));

        Long[] likedIds = currentUser.getLikedIDs();
        for (Long likedId : likedIds) {
            if (taskId.equals(likedId)) {
                throw new Exception("Task already liked");
            }
        }

        likedIds = Arrays.copyOf(likedIds, likedIds.length + 1);
        likedIds[likedIds.length - 1] = taskId;
        currentUser.setLikedIDs(likedIds);
        likedTask.setLikeCount(likedTask.getLikeCount() + 1);

        userRepository.save(currentUser);
        taskRepository.save(likedTask);
    }
}
