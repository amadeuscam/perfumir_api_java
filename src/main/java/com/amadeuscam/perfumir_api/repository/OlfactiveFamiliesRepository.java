package com.amadeuscam.perfumir_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.amadeuscam.perfumir_api.entities.OlfactiveFamilies;

@Repository
public interface OlfactiveFamiliesRepository extends JpaRepository<OlfactiveFamilies, Long> {
    @Query("SELECT DISTINCT name FROM OlfactiveFamilies")
    List<String> findDistinctByName();
}
