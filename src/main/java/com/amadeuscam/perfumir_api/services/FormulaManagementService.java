package com.amadeuscam.perfumir_api.services;

import java.util.List;

import com.amadeuscam.perfumir_api.entities.FormulaManagement;
import com.amadeuscam.perfumir_api.entities.Project;

public interface FormulaManagementService {

    // Formula addFormulaToFormulaManagement(Formula formula, Long
    // formulaManagementId);

    List<FormulaManagement> findAllFormulaManagement();

    FormulaManagement createFormulaManagement(FormulaManagement formulaManagement, Long projectId);

    FormulaManagement updateFormulaManagement(FormulaManagement formulaManagement, Long projectId);

    FormulaManagement getFormulaManagement(Long id, Long projectId);

    Project deleteFormulaManagement(Long id, Long projectId);
}
