package com.heroku.labshare.service;

import com.heroku.labshare.json.SearchResponse;
import com.heroku.labshare.json.TaskJson;
import com.heroku.labshare.json.filter.Filter;
import com.heroku.labshare.model.AdvancedSearchMap;
import com.heroku.labshare.model.Task;
import com.heroku.labshare.repository.AdvancedSearchMapRepository;
import com.heroku.labshare.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.heroku.labshare.constant.FiltersNames.*;
import static com.heroku.labshare.constant.FiltersNames.SUBJECT;

@Service
@RequiredArgsConstructor
public class AdvancedSearchService {

    private final SearchService searchService;
    private final TaskRepository taskRepository;
    private final AdvancedSearchMapRepository advancedSearchMapRepository;

    public SearchResponse search(String input) {
        return wrapTasks(searchInMap(input));
    }

    public SearchResponse search(String input, MultiValueMap<String, String> filters) {
        List<String> facultyOptionsId = searchService.getFiltersOrEmpty(filters, FACULTY);
        List<String> specialityOptionsId = searchService.getFiltersOrEmpty(filters, SPECIALITY);
        List<String> subjectOptionsId = searchService.getFiltersOrEmpty(filters, SUBJECT);

        List<Task> allTasks = searchInMap(input);

        List<Task> filteredTasks = allTasks.stream()
                .filter(task -> searchService.applyFacultyFilter(facultyOptionsId, task))
                .filter(task -> searchService.applySpecialityFilter(specialityOptionsId, task))
                .filter(task -> searchService.applySubjectFilter(subjectOptionsId, task))
                .collect(Collectors.toList());

        return wrapTasks(filteredTasks);
    }

    private List<Task> searchInMap(String input) {
        String[] inputStrings = input.split("//s+");
        return Arrays.stream(inputStrings)
                .map(advancedSearchMapRepository::findByWord)
                .flatMap(Collection::stream)
                .map(AdvancedSearchMap::getTask)
                .collect(Collectors.toList());
    }

    private SearchResponse wrapTasks(List<Task> tasks) {
        Filter[] allFilters = searchService.getAllFilters();
        TaskJson[] allTaskJsons = tasks.stream()
                .map(TaskJson::new)
                .toArray(TaskJson[]::new);
        return new SearchResponse(searchService.getOnlyPresentFilters(allFilters, allTaskJsons), allTaskJsons);
    }
}
