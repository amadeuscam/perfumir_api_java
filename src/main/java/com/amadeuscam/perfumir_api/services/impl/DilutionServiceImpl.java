package com.amadeuscam.perfumir_api.services.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.amadeuscam.perfumir_api.dto.DilutionCountDto;
import com.amadeuscam.perfumir_api.entities.Dilution;
import com.amadeuscam.perfumir_api.entities.Ingredient;
import com.amadeuscam.perfumir_api.repository.DilutionRepository;
import com.amadeuscam.perfumir_api.repository.IngredientRepository;
import com.amadeuscam.perfumir_api.services.DilutionService;

@Service
public class DilutionServiceImpl implements DilutionService {
    private final DilutionRepository dilutionRepository;
    private final IngredientRepository ingredientRepository;

    public DilutionServiceImpl(DilutionRepository dilutionRepository, IngredientRepository ingredientRepository) {
        this.dilutionRepository = dilutionRepository;
        this.ingredientRepository = ingredientRepository;
    }

    /**
     * Creates a new dilution by associating it with an existing ingredient.
     *
     * @param dilution the dilution to be created
     * @param ingredientId the ID of the ingredient to associate with the dilution
     * @return the newly created dilution
     * @throws RuntimeException if the ingredient does not exist
     */
    @Override
    public Dilution createDilution(Dilution dilution, Long ingredientId) {
        Optional<Ingredient> ingredientOptional = ingredientRepository.findById(ingredientId);
        return ingredientOptional.map(ingredient -> {
            dilution.setIngredient(ingredient);
            return dilutionRepository.save(dilution);
        }).orElseThrow(() -> new RuntimeException("Ingredient does not exist"));
    }

    /**
     * Updates an existing dilution by associating it with the given ingredient ID.
     *
     * @param dilution the dilution to be updated
     * @param ingredientId the new ID of the ingredient for this dilution
     * @return the updated dilution
     * @throws RuntimeException if the ingredient does not exist
     */
    @Override
    public Dilution updateDilution(Dilution dilution, Long ingredientId) {
        Optional<Ingredient> ingredientOptional = ingredientRepository.findById(ingredientId);
        return ingredientOptional.map(ingredient -> {
            dilution.setIngredient(ingredient);
            return dilutionRepository.save(dilution);
        }).orElseThrow(() -> new RuntimeException("Ingredient does not exist"));
    }

    /**
     * Deletes a dilution associated with the given ingredient ID.
     *
     * @param id the ID of the dilution to delete
     * @param ingredientId the ID of the ingredient from which to remove the dilution
     * @return the updated ingredient after the dilution has been deleted
     * @throws RuntimeException if the ingredient or dilution does not exist
     */
    @Override
    public Ingredient deleteDilution(Long id, Long ingredientId) {
        Optional<Ingredient> ingredientOptional = ingredientRepository.findById(ingredientId);
        return ingredientOptional.map(ingredient -> {
            ingredient.getDilutions().removeIf(d -> Objects.equals(d.getId(), id));
            return ingredientRepository.save(ingredient);
        }).orElseThrow(() -> new RuntimeException("Ingredient does not exist"));
    }

    /**
     * Retrieves a specific dilution by its ID associated with a given ingredient.
     *
     * @param id the ID of the dilution
     * @param ingredientId the ID of the ingredient to which the dilution is associated
     * @return an Optional containing the found dilution or empty if not found
     * @throws RuntimeException if the ingredient does not exist
     */
    @Override
    public Optional<Dilution> getDilution(Long id, Long ingredientId) {
        Optional<Ingredient> ingredientOptional = ingredientRepository.findById(ingredientId);
        return ingredientOptional.map(ingredient -> {
            return ingredient.getDilutions().stream().filter(d -> Objects.equals(d.getId(), id)).findFirst();
        }).orElseThrow(() -> new RuntimeException("Ingredient does not exist"));
    }

    /**
     * Retrieves all dilutions associated with a given ingredient.
     *
     * @param ingredientId the ID of the ingredient for which to retrieve dilutions
     * @return a Set containing all the dilutions associated with the ingredient
     * @throws RuntimeException if the ingredient does not exist
     */
    @Override
    public Set<Dilution> getDilutions(Long ingredientId) {
        Optional<Ingredient> ingredientOptional = ingredientRepository.findById(ingredientId);
        return ingredientOptional.map(Ingredient::getDilutions)
                .orElseThrow(() -> new RuntimeException("Ingredient does not exist"));
    }

    /**
     * Retrieves statistics on the quantity of dilution ingredients.
     *
     * @return a List containing DilutionCountDto objects with the count of each dilation
     */
    @Override
    public List<DilutionCountDto> getDilutionsQuantities() {
        return dilutionRepository.getCountByQuantity();
    }

    /**
     * Checks if a dilution exists by its ID associated with a given ingredient.
     *
     * @param id the ID of the dilution
     * @param ingredientId the ID of the ingredient to which the dilution is associated
     * @return true if the dilution exists, false otherwise
     * @throws RuntimeException if the ingredient does not exist
     */
    @Override
    public boolean isDilutionExists(Long id, Long ingredientId) {
        Optional<Ingredient> ingredientOptional = ingredientRepository.findById(ingredientId);
        return ingredientOptional
                .map(ingredient -> ingredient.getDilutions().stream().anyMatch(d -> Objects.equals(d.getId(), id)))
                .orElseThrow(() -> new RuntimeException("Ingredient does not exist"));
    }
}