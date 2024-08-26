package com.amadeuscam.perfumir_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.amadeuscam.perfumir_api.entities.FormulaIngredient;
@Repository
public interface FormulaIngredientRepository extends JpaRepository<FormulaIngredient,Long> {
}
