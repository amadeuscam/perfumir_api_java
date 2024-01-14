package com.amadeuscam.perfumir_api.services.impl;

import com.amadeuscam.perfumir_api.entities.Ingredient;
import com.amadeuscam.perfumir_api.repository.IngredientRepository;
import com.amadeuscam.perfumir_api.services.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class IngredientServiceImpl  implements IngredientService {

    private final IngredientRepository ingredientRepository;


    @Override
    public Ingredient createIngredient(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }

    @Override
    public List<Ingredient> findAll() {
        return StreamSupport.stream(ingredientRepository.findAll().spliterator(),false)
                .collect(Collectors.toList());
    }
}
