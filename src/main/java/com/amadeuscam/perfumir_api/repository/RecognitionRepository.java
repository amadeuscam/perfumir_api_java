package com.amadeuscam.perfumir_api.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.amadeuscam.perfumir_api.entities.Recognition;

@Repository
public interface RecognitionRepository extends JpaRepository<Recognition, Long> {
    List<Recognition> findDistinctByCreated(LocalDate date);
}
