package com.amadeuscam.perfumir_api.services;

import com.amadeuscam.perfumir_api.entities.Ingredient;

import java.util.List;
import java.util.Optional;

public interface IngredientService {

    Ingredient createIngredient(Ingredient ingredient);
    Ingredient partialUpdate(Ingredient ingredient);
    Optional<Ingredient> getIngredient(Long id);
    List<Ingredient> findAll();

    boolean isExists(Long id);

    void delete(Long id);

}
