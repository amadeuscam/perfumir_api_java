package com.amadeuscam.perfumir_api.services;

import com.amadeuscam.perfumir_api.dto.DilutionCountDto;
import com.amadeuscam.perfumir_api.entities.Dilution;
import com.amadeuscam.perfumir_api.entities.Ingredient;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface DilutionService {

    Dilution createDilution(Dilution dilution, Long ingredientId);

    Dilution updateDilution(Dilution dilution, Long ingredientId);

    Ingredient deleteDilution(Long id, Long ingredientId);

    Optional<Dilution> getDilution(Long id, Long ingredientId);

    Set<Dilution> getDilutions(Long ingredientId);

    List<DilutionCountDto> getDilutionsQuantities();

    boolean isDilutionExists(Long id, Long ingredientId);

}
