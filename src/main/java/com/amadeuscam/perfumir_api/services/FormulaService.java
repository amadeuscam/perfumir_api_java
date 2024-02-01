package com.amadeuscam.perfumir_api.services;

import com.amadeuscam.perfumir_api.entities.Formula;
import com.amadeuscam.perfumir_api.entities.Project;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface FormulaService {

    List<Formula> findAllFormulas();

    Set<Formula> findAllFormulasByProject(Long projectID);

    Formula createFormula(Formula formula, Long projectID);

    Formula updateFormula(Formula formula, Long projectID);

    Optional<Formula> getFormula(Long id, Long projectID);

    boolean isFormulaExists(Long id);

    Project deleteFormula(Long projectID, Long formulaId);
}
