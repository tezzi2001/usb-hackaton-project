package com.heroku.labshare.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.heroku.labshare.model.Task;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskJson implements Serializable {

    private String faculty;
    private String specialty;
    @JsonProperty("title")
    private String topic;
    private String description;
    private String subject;
    private int year;

    public Task toTask(String filePath) {
        return Task.builder()
                .year(year)
                .description(this.description)
                .faculty(this.faculty)
                .topic(this.topic)
                .subject(this.subject)
                .specialty(this.specialty)
                .filePath(filePath)
                .build();
    }
}
