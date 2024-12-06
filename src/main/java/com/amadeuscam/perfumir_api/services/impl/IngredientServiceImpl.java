package com.amadeuscam.perfumir_api.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amadeuscam.perfumir_api.entities.Ingredient;
import com.amadeuscam.perfumir_api.repository.IngredientRepository;
import com.amadeuscam.perfumir_api.services.IngredientService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IngredientServiceImpl implements IngredientService {

    @Autowired
    private final IngredientRepository ingredientRepository;

    /**
     * Creates a new ingredient.
     *
     * @param ingredient the ingredient entity to be created
     * @return the savedIngredient entity
     */
    @Override
    public Ingredient createIngredient(Ingredient ingredient) {
        Ingredient ingredientToBeSaved = ingredient;
        if (!(ingredient.getDilutions() == null) && !ingredient.getDilutions().isEmpty()) {
            ingredient.getDilutions().forEach(d -> d.setIngredient(ingredientToBeSaved));
        }

        if (!(ingredient.getOlfactiveFamilies() == null) && !ingredient.getOlfactiveFamilies().isEmpty()) {
            ingredient.getOlfactiveFamilies().forEach(d -> d.setIngredient(ingredientToBeSaved));
        }
        return ingredientRepository.save(ingredientToBeSaved);
    }

    /**
     * Updates an existing ingredient.
     *
     * @param ingredient the ingredient entity to be updated
     * @return the updated ingredient entity
     */
    @Override
    public Ingredient updateIngredient(Ingredient ingredient) {
        Ingredient ingredientToUpdate = ingredient;
        if (!(ingredient.getDilutions() == null) && !ingredient.getDilutions().isEmpty()) {
            ingredient.getDilutions().forEach(d -> d.setIngredient(ingredientToUpdate));
        }

        if (!(ingredient.getOlfactiveFamilies() == null) && !ingredient.getOlfactiveFamilies().isEmpty()) {
            ingredient.getOlfactiveFamilies().forEach(d -> d.setIngredient(ingredientToUpdate));
        }

        return ingredientRepository.save(ingredientToUpdate);
    }

    /**
     * Partially updates an existing ingredient.
     *
     * @param ingredient the partially updated ingredient entity
     * @return the update ingredient entity
     */
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

    /**
     * Retrieves an ingredient by its ID.
     *
     * @param id the ingredient's ID
     * @return the Optional containing the found ingredient if it exists, or an empty Optional otherwise
     */
    @Override
    public Optional<Ingredient> getIngredient(Long id) {
        return ingredientRepository.findById(id);
    }

    /**
     * Retrieves all ingredients.
     *
     * @return a list of all Ingredient entities
     */
    @Override
    public List<Ingredient> findAll() {
        return new ArrayList<>(ingredientRepository.findAll());
    }

    /**
     * Checks if an ingredient exists by its ID.
     *
     * @param id the ingredient's ID
     * @return true if the ingredient exists, false otherwise
     */
    @Override
    public boolean isExists(Long id) {
        return ingredientRepository.existsById(id);
    }

    /**
     * Deletes an ingredient by its ID.
     *
     * @param id the ingredient's ID
     */
    @Override
    public void delete(Long id) {
        ingredientRepository.deleteById(id);
    }
}