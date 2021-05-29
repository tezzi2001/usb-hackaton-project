package com.heroku.labshare.service;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import com.heroku.labshare.json.TaskJson;
import com.heroku.labshare.json.UserJson;
import com.heroku.labshare.model.Task;
import com.heroku.labshare.model.User;
import com.heroku.labshare.repository.TaskRepository;
import com.heroku.labshare.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class DataService {

    private static final String DELIMITER = "/";
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final DbxClientV2 dbxClient;

    @Transactional
    public void saveTask(TaskJson taskJson, MultipartFile file, String token) {
        if (!file.isEmpty()) {
            try {
                UserJson userInfo = userService.getUserInfo(token);
                Long id = userInfo.getId();
                String path = DELIMITER + id + DELIMITER + file.getOriginalFilename();
                dbxClient.files()
                        .upload(path)
                        .uploadAndFinish(file.getInputStream());
                Task task = taskJson.toTask(path);
                User user = userRepository.findById(id).orElseThrow();
                task.setUser(user);
                taskRepository.save(task);
            } catch (DbxException | IOException e) {
                e.printStackTrace();
            }
        }
    }
}
