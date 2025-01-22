package com.amadeuscam.perfumir_api.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.amadeuscam.perfumir_api.entities.Formula;
import com.amadeuscam.perfumir_api.entities.FormulaManagement;
import com.amadeuscam.perfumir_api.repository.FormulaManagementRepository;
import com.amadeuscam.perfumir_api.repository.FormulaRepository;
import com.amadeuscam.perfumir_api.services.FormulaService;

@Service
public class FormulaServiceImpl implements FormulaService {

    private final FormulaRepository formulaRepository;

    private final FormulaManagementRepository fManagementRepository;

    /**
     * Constructs a new instance of {@link FormulaServiceImpl}.
     *
     * @param formulaRepository The repository to interact with the formulas.
     * @param fManagementRepository The repository to interact with the formula management entities.
     */
    public FormulaServiceImpl(FormulaRepository formulaRepository, FormulaManagementRepository fmanagement) {
        this.formulaRepository = formulaRepository;
        this.fManagementRepository = fmanagement;
    }

    /**
     * Retrieves all formulas.
     *
     * @return A list of all formulas in the system.
     */
    @Override
    public List<Formula> findAllFormulas() {
        return formulaRepository.findAll();
    }

    /**
     * Retrieve all formulas associated with a specific formula management entity.
     *
     * @param formulaManagementId The ID of the formula management to fetch formulas for.
     * @return A set of formulas from the specified formula management.
     */
    @Override
    public Set<Formula> findAllFormulasByFormulaManagement(Long formulaManagementId) {
        final Optional<FormulaManagement> fOptional = fManagementRepository.findById(formulaManagementId);
        return fOptional.map(FormulaManagement::getFormulas)
                .orElseThrow(() -> new RuntimeException("Formula Management does not exist"));
    }

    /**
     * Adds a new formula to the specified formula management entity.
     *
     * @param formula The formula to add.
     * @param formulaManagementId The ID of the formula management to add to.
     * @return The updated formula management after adding it.
     */
    @Override
    public Formula createFormula(Formula formula, Long formulaManagementId) {
        final Optional<FormulaManagement> fOptional = fManagementRepository.findById(formulaManagementId);
        return fOptional.map(fmanagement -> {
            formula.setFormulaManagement(fmanagement);
            return formulaRepository.save(formula);

        }).orElseThrow(() -> new RuntimeException("Formula Management does not exist"));
    }

    /**
     * Updates an existing formula in the specified formula management entity.
     *
     * @param formula The updated formula.
     * @param formulaManagementId The ID of the formula management to update.
     * @return The updated formula management after updating it.
     */
    @Override
    public Formula updateFormula(Formula formula, Long formulaManagementId) {
        final Optional<FormulaManagement> fOptional = fManagementRepository.findById(formulaManagementId);
        return fOptional.map(fmanagement -> {
            formula.setFormulaManagement(fmanagement);
            return formulaRepository.save(formula);

        }).orElseThrow(() -> new RuntimeException("Formula Management does not exist"));
    }

    /**
     * Retrieves a specific formula from the specified formula management entity.
     *
     * @param formulaManagementId The ID of the formula management to fetch the formula from.
     * @param id The ID of the formula to retrieve.
     * @return An optional containing the retrieved formula if it exists, or an empty optional otherwise.
     */
    @Override
    public Optional<Formula> getFormula(Long formulaManagementId, Long id) {
        final Optional<FormulaManagement> fOptional = fManagementRepository.findById(formulaManagementId);
        return fOptional.map(fmanagement -> formulaRepository.findById(id))
                .orElseThrow(() -> new RuntimeException("Formula Management does not exist"));
    }

    /**
     * Checks if a formula exists with the specified ID.
     *
     * @param id The ID of the formula to check for existence.
     * @return true if the formula exists, false otherwise.
     */
    @Override
    public boolean isFormulaExists(Long id) {
        return formulaRepository.existsById(id);
    }

    /**
     * Deletes a formula from the specified formula management entity.
     *
     * @param formulaManagementId The ID of the formula management to delete from.
     * @param formulaId The ID of the formula to delete.
     * @return The updated formula management after deleting it.
     */
    @Override
    public FormulaManagement deleteFormula(Long formulaManagementId, Long formulaId) {
        final Optional<FormulaManagement> fOptional = fManagementRepository.findById(formulaManagementId);
        return fOptional.map(fmanagement -> {
            fmanagement.getFormulas().removeIf(s -> s.getId().equals(formulaId));
            return fManagementRepository.save(fmanagement);

        }).orElseThrow(() -> new RuntimeException("Formula Management does not exist"));
    }
}