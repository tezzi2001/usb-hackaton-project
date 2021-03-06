package com.heroku.labshare.json;

import com.heroku.labshare.model.Role;
import com.heroku.labshare.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserJson implements Serializable {

    private Long id;
    private boolean isApproved;
    private String username;
    private String email;
    private String password;
    private int faculty;
    private int specialty;

    public UserJson(User user) {
        this.id = user.getId();
        this.isApproved = user.getRole() == Role.APPROVED_USER;
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.faculty = user.getFaculty();
        this.specialty = user.getSpecialty();
    }
}
