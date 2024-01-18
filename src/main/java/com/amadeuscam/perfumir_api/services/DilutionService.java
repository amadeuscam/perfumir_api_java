package com.amadeuscam.perfumir_api.services;

import com.amadeuscam.perfumir_api.entities.Dilution;

import java.util.List;
import java.util.Optional;

public interface DilutionService {

    Dilution createDilution(Dilution dilution);

    Dilution updateDilution(Dilution dilution);

    Optional<Dilution> getDilution(Long id);

    List<Dilution> getDilutions();

    boolean isDilutionExists(Long id);

}