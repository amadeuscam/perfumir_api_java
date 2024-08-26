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

    public FormulaServiceImpl(FormulaRepository formulaRepository, FormulaManagementRepository fmanagement) {
        this.formulaRepository = formulaRepository;
        this.fManagementRepository = fmanagement;
    }

    @Override
    public List<Formula> findAllFormulas() {
        return formulaRepository.findAll();
    }

    @Override
    public Set<Formula> findAllFormulasByFormulaManagement(Long formulaManagementId) {
        final Optional<FormulaManagement> fOptional = fManagementRepository.findById(formulaManagementId);
        return fOptional.map(FormulaManagement::getFormulas)
                .orElseThrow(() -> new RuntimeException("Formula Management does not exist"));

    }

    @Override
    public Formula createFormula(Formula formula, Long formulaManagementId) {
        final Optional<FormulaManagement> fOptional = fManagementRepository.findById(formulaManagementId);
        return fOptional.map(fmanagement -> {
            formula.setFormulaManagement(fmanagement);
            return formulaRepository.save(formula);

        }).orElseThrow(() -> new RuntimeException("Formula Management does not exist"));
    }

    @Override
    public Formula updateFormula(Formula formula, Long formulaManagementId) {
        final Optional<FormulaManagement> fOptional = fManagementRepository.findById(formulaManagementId);
        return fOptional.map(fmanagement -> {
            formula.setFormulaManagement(fmanagement);
            return formulaRepository.save(formula);

        }).orElseThrow(() -> new RuntimeException("Formula Management does not exist"));
    }

    @Override
    public Optional<Formula> getFormula(Long formulaManagementId, Long id) {
        final Optional<FormulaManagement> fOptional = fManagementRepository.findById(formulaManagementId);
        return fOptional.map(fmanagement -> formulaRepository.findById(id))
                .orElseThrow(() -> new RuntimeException("Formula Management does not exist"));
    }

    @Override
    public boolean isFormulaExists(Long id) {
        return formulaRepository.existsById(id);
    }

    @Override
    public FormulaManagement deleteFormula(Long formulaManagementId, Long formulaId) {
        final Optional<FormulaManagement> fOptional = fManagementRepository.findById(formulaManagementId);
        return fOptional.map(fmanagement -> {
            fmanagement.getFormulas().removeIf(s -> s.getId().equals(formulaId));
            return fManagementRepository.save(fmanagement);
        }).orElseThrow(() -> new RuntimeException("Formula Management does not exist"));
    }

}
