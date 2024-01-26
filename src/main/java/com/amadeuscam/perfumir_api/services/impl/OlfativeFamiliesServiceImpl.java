package com.amadeuscam.perfumir_api.services.impl;

import com.amadeuscam.perfumir_api.entities.Ingredient;
import com.amadeuscam.perfumir_api.entities.OlfactiveFamilies;
import com.amadeuscam.perfumir_api.repository.IngredientRepository;
import com.amadeuscam.perfumir_api.repository.OlfactiveFamiliesRepository;
import com.amadeuscam.perfumir_api.services.OlfativeFamiliesService;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public Set<OlfactiveFamilies> getOlfactiveFamiliess(Long ingredientId) {
        return ingredientRepository.findById(ingredientId).map(Ingredient::getOlfactiveFamilies)
                .orElseThrow(() -> new RuntimeException("Ingredient does not exist"));
    }

    @Override
    public List<String> getUniqueOlfactiveFamilies() {
        return olfactiveFamiliesRepository.findDistinctByName();
    }
}
