package com.amadeuscam.perfumir_api.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.amadeuscam.perfumir_api.entities.Ingredient;
import com.amadeuscam.perfumir_api.entities.OlfactiveFamilies;

public interface OlfativeFamiliesService {

    OlfactiveFamilies createOlfactiveFamilies(OlfactiveFamilies olfactiveFamilies, Long ingredientId);

    OlfactiveFamilies updateOlfactiveFamilies(OlfactiveFamilies olfactiveFamilies, Long ingredientId);

    Ingredient deleteOlfactiveFamilies(Long id, Long ingredientId);

    Optional<OlfactiveFamilies> getOlfactiveFamilies(Long id, Long ingredientId);

    Set<OlfactiveFamilies> getOlfactiveFamiliess(Long ingredientId);

    List<String> getUniqueOlfactiveFamilies();
}
