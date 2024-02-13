package com.amadeuscam.perfumir_api.services.impl;

import com.amadeuscam.perfumir_api.entities.Ingredient;
import com.amadeuscam.perfumir_api.entities.OlfactiveFamilies;
import com.amadeuscam.perfumir_api.repository.IngredientRepository;
import com.amadeuscam.perfumir_api.repository.OlfactiveFamiliesRepository;
import com.amadeuscam.perfumir_api.services.OlfativeFamiliesService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
public class OlfativeFamiliesServiceImpl implements OlfativeFamiliesService {


    private final IngredientRepository ingredientRepository;
    private final OlfactiveFamiliesRepository olfactiveFamiliesRepository;

    public OlfativeFamiliesServiceImpl(IngredientRepository ingredientRepository, OlfactiveFamiliesRepository olfactiveFamiliesRepository) {
        this.ingredientRepository = ingredientRepository;
        this.olfactiveFamiliesRepository = olfactiveFamiliesRepository;
    }


    @Override
    public OlfactiveFamilies createOlfactiveFamilies(OlfactiveFamilies olfactiveFamilies, Long ingredientId) {
        Optional<Ingredient> ingredientOptional = ingredientRepository.findById(ingredientId);
        return ingredientOptional.map(ingredient -> {
            olfactiveFamilies.setIngredient(ingredient);
            return olfactiveFamiliesRepository.save(olfactiveFamilies);
        }).orElseThrow(() -> new RuntimeException("Ingredient does not exist"));
    }

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

    @Override
    public Ingredient deleteOlfactiveFamilies(Long id, Long ingredientId) {
        Optional<Ingredient> ingredientOptional = ingredientRepository.findById(ingredientId);

        return ingredientOptional.map(ingredient -> {

            if (ingredient.getOlfactiveFamilies().stream().noneMatch(d -> Objects.equals(d.getId(), id))){
                throw  new RuntimeException("OLfatives Id does not found");
            }
            ingredient.getOlfactiveFamilies().removeIf(d -> Objects.equals(d.getId(), id));
            return ingredientRepository.save(ingredient);
        }).orElseThrow(() -> new RuntimeException("Ingredient does not exist"));
    }

    @Override
    public Optional<OlfactiveFamilies> getOlfactiveFamilies(Long id, Long ingredientId) {
        Optional<Ingredient> ingredientOptional = ingredientRepository.findById(ingredientId);
        return ingredientOptional.map(ingredient -> {
            return ingredient.getOlfactiveFamilies().stream().filter(d -> Objects.equals(d.getId(), id)).findFirst();
        }).orElseThrow(() -> new RuntimeException("Ingredient does not exist"));
    }

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
