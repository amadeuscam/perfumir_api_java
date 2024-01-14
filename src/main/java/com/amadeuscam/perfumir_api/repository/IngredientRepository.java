package com.amadeuscam.perfumir_api.repository;

import com.amadeuscam.perfumir_api.entities.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
}
