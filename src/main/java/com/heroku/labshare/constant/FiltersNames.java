package com.heroku.labshare.constant;

import java.util.HashSet;
import java.util.Set;

//TODO: refactor
public final class FiltersNames {

    public static final String FACULTY = "faculty";
    public static final String SPECIALITY = "speciality";
    public static final String SUBJECT = "subject";

    private FiltersNames() {
    }

    public static Set<String> getFiltersNames() {
        Set<String> filtersNames = new HashSet<>();
        filtersNames.add(FACULTY);
        filtersNames.add(SPECIALITY);
        filtersNames.add(SUBJECT);
        return filtersNames;
    }
}
