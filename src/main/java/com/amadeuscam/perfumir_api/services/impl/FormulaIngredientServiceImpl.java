package com.amadeuscam.perfumir_api.services.impl;

import com.amadeuscam.perfumir_api.entities.Formula;
import com.amadeuscam.perfumir_api.entities.FormulaIngredient;
import com.amadeuscam.perfumir_api.repository.FormulaIngredientRepository;
import com.amadeuscam.perfumir_api.repository.FormulaRepository;
import com.amadeuscam.perfumir_api.services.FormulaIngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
public class FormulaIngredientServiceImpl implements FormulaIngredientService {

    private final FormulaIngredientRepository formulaIngredientRepository;
    private final FormulaRepository formulaRepository;

    @Autowired
    public FormulaIngredientServiceImpl(FormulaIngredientRepository formulaIngredientRepository, FormulaRepository formulaRepository) {
        this.formulaIngredientRepository = formulaIngredientRepository;
        this.formulaRepository = formulaRepository;
    }

    @Override
    public List<FormulaIngredient> findAllFormulaIngredient() {
        return formulaIngredientRepository.findAll();
    }

    @Override
    public Set<FormulaIngredient> findAllFormulaIngredientsByFormula(Long formulaID) {
        final Optional<Formula> formulaOptional = formulaRepository.findById(formulaID);
        return formulaOptional.map(Formula::getFormulaIngredients).orElseThrow(() -> new RuntimeException("Formula does not exist"));


    }

    @Override
    public FormulaIngredient addFormulaIngredientToFormula(FormulaIngredient formulaIngredient, Long formulaID) {
        final Optional<Formula> optionalFormula = formulaRepository.findById(formulaID);
        return optionalFormula.map(formula -> {
            formulaIngredient.setFormula(formula);
            return formulaIngredientRepository.save(formulaIngredient);
        }).orElseThrow(() -> new RuntimeException("Formula does not exist"));
    }

    @Override
    public FormulaIngredient updateFormulaIngredientFromFormula(FormulaIngredient formulaIngredient, Long formulaID) {
        final Optional<Formula> optionalFormula = formulaRepository.findById(formulaID);
        return optionalFormula.map(formula -> {
            formulaIngredient.setFormula(formula);
            return formulaIngredientRepository.save(formulaIngredient);
        }).orElseThrow(() -> new RuntimeException("Formula does not exist"));
    }

    @Override
    public Formula deleteFormulaIngredient(Long formulaID, Long formulaIngredientId) {
        final Optional<Formula> optionalFormula = formulaRepository.findById(formulaID);
        return optionalFormula.map(formula -> {
            formula.getFormulaIngredients().removeIf(s -> s.getId().equals(formulaIngredientId));
            return formulaRepository.save(formula);
        }).orElseThrow(() -> new RuntimeException("Formula does not exist"));
    }

    @Override
    public Optional<FormulaIngredient> getFormulaIngredientFromFormula(Long formulaID, Long formulaIngredientId) {
        final Optional<Formula> optionalFormula = formulaRepository.findById(formulaID);
        return optionalFormula.map(formula -> formula.getFormulaIngredients()
                .stream().
                filter(formulaIngredient -> Objects.equals(formulaIngredient.getId(), formulaIngredientId))
                .findFirst()).orElseThrow(() -> new RuntimeException("Formula does not exist"));
    }

    @Override
    public boolean isFormulaIngredientExists(Long id) {
        return formulaIngredientRepository.existsById(id);
    }


}
