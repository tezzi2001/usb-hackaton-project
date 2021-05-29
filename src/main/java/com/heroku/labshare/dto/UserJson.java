package com.heroku.labshare.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserJson implements Serializable {

    private String username;
    private String email;
    private String password;
    private String faculty;
    private String specialty;
}
