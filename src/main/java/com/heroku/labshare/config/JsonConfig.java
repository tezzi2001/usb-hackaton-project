package com.heroku.labshare.config;

import static java.nio.charset.Charset.defaultCharset;
import static org.apache.logging.log4j.util.Strings.EMPTY;

import java.io.IOException;
import java.util.Objects;

import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heroku.labshare.json.faculty.Faculty;
import com.heroku.labshare.json.specialty.Specialty;
import com.heroku.labshare.json.subject.Subject;

import lombok.SneakyThrows;

@Configuration
public class JsonConfig {

    @Bean
    public ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }

    @SneakyThrows
    public Faculty[] getFaculties() {
        final String facultyJson = readJson("/json/faculties.json");
        return getObjectMapper().readValue(facultyJson, Faculty[].class);
    }

    @SneakyThrows
    public Specialty[] getSpecialties() {
        final String specialtyJson = readJson("/json/specialties.json");
        return getObjectMapper().readValue(specialtyJson, Specialty[].class);
    }

    @SneakyThrows
    public Subject[] getSubjects() {
        final String subjectJson = readJson("/json/subjects.json");
        return getObjectMapper().readValue(subjectJson, Subject[].class);
    }

    private String readJson(String path) {
        try {
            return IOUtils.toString(Objects.requireNonNull(this.getClass()
                .getResourceAsStream(path)), defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return EMPTY;
    }
}
