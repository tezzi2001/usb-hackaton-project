package com.heroku.labshare.repository;

import com.heroku.labshare.model.AdvancedSearchMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdvancedSearchMapRepository extends JpaRepository<AdvancedSearchMap, Long> {

    List<AdvancedSearchMap> findByWordContaining(String word);
}
