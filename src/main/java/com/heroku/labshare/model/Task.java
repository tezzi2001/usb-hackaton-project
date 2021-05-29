package com.heroku.labshare.model;

import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.List;

@Data
@Entity(name = "tasks")
@Builder
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
    @ColumnDefault("0")
    private Integer likeCount;

    @ManyToOne()
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany(mappedBy = "task")
    private List<Report> reports;
}