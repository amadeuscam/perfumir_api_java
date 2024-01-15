package com.amadeuscam.perfumir_api.services.impl;

import com.amadeuscam.perfumir_api.entities.Ingredient;
import com.amadeuscam.perfumir_api.repository.IngredientRepository;
import com.amadeuscam.perfumir_api.services.IngredientService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
@Transactional
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;


    @Override
    public Ingredient createIngredient(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
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
