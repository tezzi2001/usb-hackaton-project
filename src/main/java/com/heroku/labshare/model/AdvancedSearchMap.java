package com.heroku.labshare.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class AdvancedSearchMap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String word;

    @OneToOne
    @JoinColumn(name = "task_id")
    private Task task;
}
