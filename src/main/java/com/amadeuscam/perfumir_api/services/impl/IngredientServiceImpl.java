package com.amadeuscam.perfumir_api.services.impl;

import com.amadeuscam.perfumir_api.entities.Dilution;
import com.amadeuscam.perfumir_api.entities.Ingredient;
import com.amadeuscam.perfumir_api.repository.DilutionRepository;
import com.amadeuscam.perfumir_api.repository.IngredientRepository;
import com.amadeuscam.perfumir_api.services.IngredientService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class IngredientServiceImpl implements IngredientService {
    @Autowired
    private final IngredientRepository ingredientRepository;
    private final DilutionRepository dilutionRepository;


    @Override
    public Ingredient createIngredient(Ingredient ingredient) {
        Ingredient ingredientToBeSaved = ingredient;
        if (!(ingredient.getDilutions() == null) && !ingredient.getDilutions().isEmpty()) {
            ingredient.getDilutions().forEach(d -> d.setIngredient(ingredientToBeSaved));
        }
        return ingredientRepository.save(ingredientToBeSaved);
    }

    @Override
    public Ingredient updateIngredient(Ingredient ingredient) {
        Ingredient ingredientToBeUpdated = ingredient;
        if (!(ingredient.getDilutions() == null) && !ingredient.getDilutions().isEmpty()) {
            ingredient.getDilutions().forEach(d -> d.setIngredient(ingredientToBeUpdated));
        }

        return ingredientRepository.save(ingredientToBeUpdated);


    }


    @Override
    public Ingredient partialUpdate(Ingredient ingredient) {
        return ingredientRepository.findById(ingredient.getId()).map(exitingIngredient -> {
            Optional.ofNullable(ingredient.getName()).ifPresent(exitingIngredient::setName);
            Optional.ofNullable(ingredient.getCasNumber()).ifPresent(exitingIngredient::setCasNumber);
            Optional.ofNullable(ingredient.getType()).ifPresent(exitingIngredient::setType);
            Optional.ofNullable(ingredient.getOdorImpact()).ifPresent(exitingIngredient::setOdorImpact);
            Optional.ofNullable(ingredient.getOdorLife()).ifPresent(exitingIngredient::setOdorLife);
            Optional.ofNullable(ingredient.getPyramidLevel()).ifPresent(exitingIngredient::setPyramidLevel);
            Optional.ofNullable(ingredient.getOdorDescription()).ifPresent(exitingIngredient::setOdorDescription);
            return ingredientRepository.save(ingredient);
        }).orElseThrow(() -> new RuntimeException("Ingredient does not exist"));
    }

    @Override
    public Optional<Ingredient> getIngredient(Long id) {
        return ingredientRepository.findById(id);
    }


    @Override
    public List<Ingredient> findAll() {
        return new ArrayList<>(ingredientRepository.findAll());
    }

    @Override
    public boolean isExists(Long id) {
        return ingredientRepository.existsById(id);
    }

    @Override
    public void delete(Long id) {
        ingredientRepository.deleteById(id);
    }
}
