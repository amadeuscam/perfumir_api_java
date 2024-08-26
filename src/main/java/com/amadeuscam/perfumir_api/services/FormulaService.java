package com.amadeuscam.perfumir_api.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.amadeuscam.perfumir_api.entities.Formula;
import com.amadeuscam.perfumir_api.entities.FormulaManagement;

public interface FormulaService {

    List<Formula> findAllFormulas();

    Set<Formula> findAllFormulasByFormulaManagement(Long formulaManagementId);

    Formula createFormula(Formula formula, Long formulaManagementId);

    Formula updateFormula(Formula formula, Long formulaManagementId);

    Optional<Formula> getFormula(Long id, Long formulaManagementId);

    boolean isFormulaExists(Long id);

    FormulaManagement deleteFormula(Long formulaManagementId, Long formulaId);
}
