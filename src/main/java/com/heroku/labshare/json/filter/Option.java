package com.heroku.labshare.json.filter;

import com.heroku.labshare.json.faculty.Faculty;
import com.heroku.labshare.json.specialty.Specialty;
import com.heroku.labshare.json.subject.Subject;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Option {

    public Option(Faculty faculty) {
        this.id = faculty.getId();
        this.name = faculty.getName();
    }

    public Option(Specialty specialty) {
        this.id = specialty.getId();
        this.name = specialty.getName();
    }

    public Option(Subject subject) {
        this.id = subject.getId();
        this.name = subject.getName();
    }

    private Integer id;
    private String name;
}
