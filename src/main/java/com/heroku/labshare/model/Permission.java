package com.heroku.labshare.model;

import lombok.Getter;

@Getter
public enum Permission {

    MODERATE("moderate"),
    DOWNLOAD_LAB("downloadLab"),
    DEFAULT("default");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }
}
