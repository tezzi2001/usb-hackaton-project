package com.heroku.labshare.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "reports")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;
    private String cause;
    private String description;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;
}