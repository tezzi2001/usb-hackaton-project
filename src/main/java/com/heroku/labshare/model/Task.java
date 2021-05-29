package com.heroku.labshare.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity(name = "tasks")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String faculty;
    private String specialty;
    private String topic;
    private String description;
    private String subject;
    private int year;
    @Column(nullable = false)
    private String filePath;
    private Integer likeCount;

    @ManyToOne()
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany(mappedBy = "task")
    private List<Report> reports;

    @PrePersist
    public void prePersist() {
        if(likeCount == null) {
            likeCount = 0;
        }
    }
}