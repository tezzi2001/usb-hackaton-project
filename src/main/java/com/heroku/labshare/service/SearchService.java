package com.heroku.labshare.service;

import com.heroku.labshare.json.SearchResponse;
import com.heroku.labshare.json.TaskJson;
import com.heroku.labshare.json.faculty.Faculty;
import com.heroku.labshare.json.filter.Filter;
import com.heroku.labshare.json.filter.Option;
import com.heroku.labshare.json.specialty.Specialty;
import com.heroku.labshare.json.subject.Subject;
import com.heroku.labshare.model.Task;
import com.heroku.labshare.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.heroku.labshare.constant.FiltersNames.*;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final TaskRepository taskRepository;
    private final Faculty[] faculties;
    private final Specialty[] specialties;
    private final Subject[] subjects;

    public SearchResponse search() {
        List<Task> allTasks = taskRepository.findAll();
        return search(allTasks);
    }

    public SearchResponse search(String input) {
        List<Task> allTasks = taskRepository.findTasksByTopic(input);
        return search(allTasks);
    }

    public SearchResponse search(String input, MultiValueMap<String, String> filters) {
        List<String> facultyOptionsId = getFiltersOrEmpty(filters, FACULTY);
        List<String> specialityOptionsId = getFiltersOrEmpty(filters, SPECIALITY);
        List<String> subjectOptionsId = getFiltersOrEmpty(filters, SUBJECT);

        List<Task> allTasks = taskRepository.findTasksByTopic(input);

        List<Task> filteredTasks = allTasks.stream()
                .filter(task -> applyFacultyFilter(facultyOptionsId, task))
                .filter(task -> applySpecialityFilter(specialityOptionsId, task))
                .filter(task -> applySubjectFilter(subjectOptionsId, task))
                .collect(Collectors.toList());

        return search(filteredTasks);
    }

    public SearchResponse search(MultiValueMap<String, String> filters) {
        List<String> facultyOptionsId = getFiltersOrEmpty(filters, FACULTY);
        List<String> specialityOptionsId = getFiltersOrEmpty(filters, SPECIALITY);
        List<String> subjectOptionsId = getFiltersOrEmpty(filters, SUBJECT);

        List<Task> allTasks = taskRepository.findAll();

        List<Task> filteredTasks = allTasks.stream()
                .filter(task -> applyFacultyFilter(facultyOptionsId, task))
                .filter(task -> applySpecialityFilter(specialityOptionsId, task))
                .filter(task -> applySubjectFilter(subjectOptionsId, task))
                .collect(Collectors.toList());

        return search(filteredTasks);
    }

    boolean applyFacultyFilter(List<String> optionsId, Task task) {
        return optionsId.isEmpty() || optionsId.contains(task.getFaculty().toString());
    }

    boolean applySpecialityFilter(List<String> optionsId, Task task) {
        return optionsId.isEmpty() || optionsId.contains(task.getSpecialty().toString());
    }

    boolean applySubjectFilter(List<String> optionsId, Task task) {
        return optionsId.isEmpty() || optionsId.contains(task.getSubject().toString());
    }

    private SearchResponse search(List<Task> allTasks) {
        Filter[] allFilters = getAllFilters();
        TaskJson[] allTaskJsons = allTasks.stream()
                .map(TaskJson::new)
                .toArray(TaskJson[]::new);
        return new SearchResponse(getOnlyPresentFilters(allFilters, allTaskJsons), allTaskJsons);
    }

    //TODO: refactor
    Filter[] getAllFilters() {
        Filter[] filters = new Filter[3];

        Option[] facultyOptions = Arrays.stream(faculties)
                .map(Option::new)
                .toArray(Option[]::new);
        filters[0] = new Filter(FACULTY, "Факультет", facultyOptions);

        Option[] specialityOptions = Arrays.stream(specialties)
                .map(Option::new)
                .toArray(Option[]::new);
        filters[1] = new Filter(SPECIALITY, "Спеціальність", specialityOptions);

        Option[] subjectOptions = Arrays.stream(subjects)
                .map(Option::new)
                .toArray(Option[]::new);
        filters[2] = new Filter(SUBJECT, "Предемет", subjectOptions);

        return filters;
    }

    // TODO: refactor
    Filter[] getOnlyPresentFilters(Filter[] filters, TaskJson[] taskJsons) {
        Set<Integer> facultyIdSet = Arrays.stream(taskJsons)
                .map(TaskJson::getFaculty)
                .collect(Collectors.toSet());
        Set<Integer> specialtyIdSet = Arrays.stream(taskJsons)
                .map(TaskJson::getSpecialty)
                .collect(Collectors.toSet());
        Set<Integer> subjectIdSet = Arrays.stream(taskJsons)
                .map(TaskJson::getSubject)
                .collect(Collectors.toSet());

        for (Filter filter : filters) {
            switch (filter.getId()) {
                case FACULTY:
                    changeFilter(filter, facultyIdSet);
                    break;
                case SPECIALITY:
                    changeFilter(filter, specialtyIdSet);
                    break;
                case SUBJECT:
                    changeFilter(filter, subjectIdSet);
                    break;
                default:
            }
        }
        return filters;
    }

    private void changeFilter(Filter filter, Set<Integer> idSet) {
        Option[] options = filter.getOptions();
        Option[] newOptions = getOnlyPresentOptions(options, idSet);
        filter.setOptions(newOptions);
    }

    private Option[] getOnlyPresentOptions(Option[] options, Set<Integer> presentOptionIds)  {
        List<Option> newOptions = new ArrayList<>();
        Arrays.stream(options)
                .forEach(option -> {
                    if (presentOptionIds.contains(option.getId())) {
                        newOptions.add(option);
                    }
                });
        return newOptions.toArray(new Option[0]);
    }

    List<String> getFiltersOrEmpty(MultiValueMap<String, String> filters, String filterId) {
        List<String> certainFilters = filters.get(filterId);
        return certainFilters == null ?
                new ArrayList<>() :
                certainFilters;
    }
}
