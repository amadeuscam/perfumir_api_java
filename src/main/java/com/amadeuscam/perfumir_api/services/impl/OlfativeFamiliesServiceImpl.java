package com.amadeuscam.perfumir_api.services.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.amadeuscam.perfumir_api.entities.Ingredient;
import com.amadeuscam.perfumir_api.entities.OlfactiveFamilies;
import com.amadeuscam.perfumir_api.repository.IngredientRepository;
import com.amadeuscam.perfumir_api.repository.OlfactiveFamiliesRepository;
import com.amadeuscam.perfumir_api.services.OlfativeFamiliesService;

@Service
public class OlfativeFamiliesServiceImpl implements OlfativeFamiliesService {

    private final IngredientRepository ingredientRepository;
    private final OlfactiveFamiliesRepository olfactiveFamiliesRepository;

    public OlfativeFamiliesServiceImpl(IngredientRepository ingredientRepository, OlfactiveFamiliesRepository olfactiveFamiliesRepository) {
        this.ingredientRepository = ingredientRepository;
        this.olfactiveFamiliesRepository = olfactiveFamiliesRepository;
    }

    /**
     * This method saves the Olfative family to the database using {@link OlfativeFamiliesService}
     * 
     * @param olfactiveFamilies Olfatives to be saved
     * @param ingredientId Id of theingredient
     * @return a success or error message.
     */
    @Override
    public OlfactiveFamilies createOlfactiveFamilies(OlfactiveFamilies olfactiveFamilies, Long ingredientId) {
        Optional<Ingredient> ingredientOptional = ingredientRepository.findById(ingredientId);
        return ingredientOptional.map(ingredient -> {
            olfactiveFamilies.setIngredient(ingredient);
            return olfactiveFamiliesRepository.save(olfactiveFamilies);
        }).orElseThrow(() -> new RuntimeException("Ingredient does not exist"));
    }

    /**
     * This method updates the Olfative family in the database using
     * {@link OlfativeFamiliesService}.<br>
     * 
     * @param olfactiveFamilies Olfatives to be saved
     * @param ingredientId Id of theingredient
     * @return a success or error message.
     */
    @Override
    public OlfactiveFamilies updateOlfactiveFamilies(OlfactiveFamilies olfactiveFamilies, Long ingredientId) {
        Optional<Ingredient> ingredientOptional = ingredientRepository.findById(ingredientId);
        return ingredientOptional.map(ingredient -> {
            if ((olfactiveFamilies.getId() == null)) {
                throw new RuntimeException("No id found!!!");
            }
            olfactiveFamilies.setIngredient(ingredient);
            return olfactiveFamiliesRepository.save(olfactiveFamilies);
        }).orElseThrow(() -> new RuntimeException("Ingredient does not exist"));
    }

    /**
     * This method deletes the Olfative family from the database using
     * {@link OlfativeFamiliesService}.<br>
     * 
     * @param id Olfatives' Id which you want to delete
     * @param ingredientId Id of the ingredient
     * @return a success or error message.
     */
    @Override
    public Ingredient deleteOlfactiveFamilies(Long id, Long ingredientId) {
        Optional<Ingredient> ingredientOptional = ingredientRepository.findById(ingredientId);

        return ingredientOptional.map(ingredient -> {

            if (ingredient.getOlfactiveFamilies().stream().noneMatch(d -> Objects.equals(d.getId(), id))) {
                throw new RuntimeException("OLfatives Id does not found");
            }
            ingredient.getOlfactiveFamilies().removeIf(d -> Objects.equals(d.getId(), id));
            return ingredientRepository.save(ingredient);
        }).orElseThrow(() -> new RuntimeException("Ingredient does not exist"));
    }

    /**
     * This method retrieves the {@link OlfactiveFamilies} for a given ingredient using
     * {@link OlfativeFamiliesService}.<br>
     * 
     * @param olfactiveFamilyId Olfatives' Id
     * @param ingredientId Id of the ingredient
     * @return an {@link Optional<OlfactiveFamilies>} with value if it exists, otherwise null
     */
    @Override
    public Optional<OlfactiveFamilies> getOlfactiveFamilies(Long olfactiveFamilyId, Long ingredientId) {
        Optional<Ingredient> ingredientOptional = ingredientRepository.findById(ingredientId);
        return ingredientOptional.map(ingredient -> {
            return ingredient.getOlfactiveFamilies().stream().filter(d -> Objects.equals(d.getId(), olfactiveFamilyId)).findFirst();
        }).orElseThrow(() -> new RuntimeException("Ingredient does not exist"));
    }

    /**
     * This method retrieves all the {@link OlfactiveFamilies} from a given ingredient using
     * {@link OlfativeFamiliesService}.<br>
     * 
     * @param ingredientId Id of the ingredient
     * @return an {@link Iterable<OlfactiveFamilies>} of values.
     */
    @Override
    public Set<OlfactiveFamilies> getOlfactiveFamiliess(Long ingredientId) {
        return ingredientRepository.findById(ingredientId).map(Ingredient::getOlfactiveFamilies)
                .orElseThrow(() -> new RuntimeException("Ingredient does not exist"));
    }

    @Override
    public List<String> getUniqueOlfactiveFamilies() {
        return olfactiveFamiliesRepository.findDistinctByName();
    }
}
