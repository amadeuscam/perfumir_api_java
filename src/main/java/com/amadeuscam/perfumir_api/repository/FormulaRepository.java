package com.amadeuscam.perfumir_api.repository;

import com.amadeuscam.perfumir_api.entities.Formula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormulaRepository extends JpaRepository<Formula, Long> {
}
