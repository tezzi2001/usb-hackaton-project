package com.heroku.labshare.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.heroku.labshare.model.Task;
import com.heroku.labshare.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskJson implements Serializable {

    private Long id;
    private int faculty;
    private int specialty;
    @JsonProperty("title")
    private String topic;
    private String description;
    private int subject;
    private int year;
    private Long[] likedIds;
    private String username;

    public TaskJson(Task task) {
        this.id = task.getId();
        this.faculty = task.getFaculty();
        this.specialty = task.getSpecialty();
        this.topic = task.getTopic();
        this.description = task.getDescription();
        this.subject = task.getSubject();
        this.year = task.getYear();
        this.likedIds = task.getLikedUsers().stream()
                .map(User::getId)
                .toArray(Long[]::new);
        this.username = task.getUser().getUsername();
    }

    public Task toTask(String filePath) {
        return Task.builder()
                .id(this.id)
                .year(this.year)
                .description(this.description)
                .faculty(this.faculty)
                .topic(this.topic)
                .subject(this.subject)
                .specialty(this.specialty)
                .filePath(filePath)
                .build();
    }
}
