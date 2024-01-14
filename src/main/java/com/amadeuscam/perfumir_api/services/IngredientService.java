package com.amadeuscam.perfumir_api.services;

import com.amadeuscam.perfumir_api.entities.Ingredient;

import java.util.List;

public interface IngredientService {

    Ingredient createIngredient(Ingredient ingredient);
    List<Ingredient> findAll();

}
