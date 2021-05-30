package com.heroku.labshare.service;

import com.auth0.jwt.JWT;
import com.heroku.labshare.json.UserJson;
import com.heroku.labshare.model.Role;
import com.heroku.labshare.model.Task;
import com.heroku.labshare.model.User;
import com.heroku.labshare.repository.TaskRepository;
import com.heroku.labshare.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public UserJson getUserInfo(String token) {
        String username = JWT.decode(token).getSubject();
        return new UserJson(userRepository
            .findByUsername(username)
            .orElseThrow(() -> new EntityNotFoundException("User not found by username " + username)));
    }

    public boolean isUserApprovedById(Long id) {
        User user = userRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("User not found by id " + id));
        return user.getRole() == Role.APPROVED_USER;
    }

    @Transactional
    public void like(Long userId, Long taskId) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found by id " + userId));

        Task task = taskRepository
                .findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found by id " + userId));

        List<User> likedUsers = task.getLikedUsers();
        if (likedUsers.contains(user)) {
            likedUsers.remove(user);
        } else {
            likedUsers.add(user);
        }
        task.setLikedUsers(likedUsers);

        taskRepository.save(task);
    }
}
