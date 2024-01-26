package com.amadeuscam.perfumir_api.services;

import com.amadeuscam.perfumir_api.entities.OlfactiveFamilies;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface OlfativeFamiliesService {


    Set<OlfactiveFamilies> getOlfactiveFamiliess(Long ingredientId);

    List<String> getUniqueOlfactiveFamilies();
}
