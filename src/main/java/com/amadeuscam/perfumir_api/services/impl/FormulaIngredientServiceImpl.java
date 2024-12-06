package com.amadeuscam.perfumir_api.services.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.amadeuscam.perfumir_api.entities.Formula;
import com.amadeuscam.perfumir_api.entities.FormulaIngredient;
import com.amadeuscam.perfumir_api.repository.FormulaIngredientRepository;
import com.amadeuscam.perfumir_api.repository.FormulaRepository;
import com.amadeuscam.perfumir_api.services.FormulaIngredientService;

@Service
public class FormulaIngredientServiceImpl implements FormulaIngredientService {

    private final FormulaIngredientRepository formulaIngredientRepository;
    private final FormulaRepository formulaRepository;

    public FormulaIngredientServiceImpl(FormulaIngredientRepository formulaIngredientRepository, FormulaRepository formulaRepository) {
        this.formulaIngredientRepository = formulaIngredientRepository;
        this.formulaRepository = formulaRepository;
    }


    /**
     * Retrieves all FormulaIngredients from the database.
     *
     * @return A list of all FormulaIngredients.
     */
    @Override
    public List<FormulaIngredient> findAllFormulaIngredient() {
        return formulaIngredientRepository.findAll();
    }

    /**
     * Finds all FormulaIngredients belonging to a specific Formula by its ID.
     *
     * @param formulaID The ID of the Formula whose ingredients are to be retrieved.
     * @return A set containing all FormulaIngredients associated with the given Formula.
     */
    @Override
    public Set<FormulaIngredient> findAllFormulaIngredientsByFormula(Long formulaID) {
        final Optional<Formula> formulaOptional = formulaRepository.findById(formulaID);
        return formulaOptional.map(Formula::getFormulaIngredients).orElseThrow(() -> new RuntimeException("Formula does not exist"));

    }

    /**
     * Adds a FormulaIngredient to an existing Formula.
     *
     * @param formulaIngredient The FormulaIngredient object to be added.
     * @param formulaID The ID of the Formula where the ingredient is to be added.
     * @return The saved FormulaIngredient.
     */
    @Override
    public FormulaIngredient addFormulaIngredientToFormula(FormulaIngredient formulaIngredient, Long formulaID) {
        final Optional<Formula> optionalFormula = formulaRepository.findById(formulaID);
        return optionalFormula.map(formula -> {
            formulaIngredient.setFormula(formula);
            return formulaIngredientRepository.save(formulaIngredient);
        }).orElseThrow(() -> new RuntimeException("Formula does not exist"));
    }

    /**
     * Adds multiple FormulaIngredients to an existing Formula.
     *
     * @param formulaIngredients A list of FormulaIngredients objects to be added.
     * @param formulaID The ID of the Formula where the ingredients are to be added.
     */
    @Override
    public void addFormulaIngredientsToFormula(List<FormulaIngredient> formulaIngredients, Long formulaID) {

        final Optional<Formula> optionalFormula = formulaRepository.findById(formulaID);

        if (optionalFormula.isPresent()) {
            formulaIngredients.forEach(d -> {
                d.setFormula(optionalFormula.get());
                formulaIngredientRepository.save(d);
            });
        } else {
            new RuntimeException("Formula does not exist");
        }


    }

    /**
     * Updates an existing FormulaIngredient within a specific Formula.
     *
     * @param formulaIngredient The updated FormulaIngredient object to be saved.
     * @param formulaID The ID of the Formula where the ingredient is located.
     * @return The saved FormulaIngredient.
     */
    @Override
    public FormulaIngredient updateFormulaIngredientFromFormula(FormulaIngredient formulaIngredient, Long formulaID) {
        final Optional<Formula> optionalFormula = formulaRepository.findById(formulaID);
        return optionalFormula.map(formula -> {

            if (formula.getFormulaIngredients().stream().noneMatch(d -> Objects.equals(d.getId(), formulaIngredient.getId()))) {
                throw new RuntimeException("FormulaIngredient Id does not found");
            }
            formulaIngredient.setFormula(formula);
            return formulaIngredientRepository.save(formulaIngredient);
        }).orElseThrow(() -> new RuntimeException("Formula does not exist"));
    }

    /**
     * Deletes a FormulaIngredient from an existing Formula.
     *
     * @param formulaID The ID of the Formula where the ingredient is located.
     * @param formulaIngredientId The ID of the FormulaIngredient to be deleted.
     */
    @Override
    public Formula deleteFormulaIngredient(Long formulaID, Long formulaIngredientId) {
        final Optional<Formula> optionalFormula = formulaRepository.findById(formulaID);
        return optionalFormula.map(formula -> {

            if (formula.getFormulaIngredients().stream().noneMatch(d -> Objects.equals(d.getId(), formulaIngredientId))) {
                throw new RuntimeException("FormulaIngredient Id does not found");
            }

            formula.getFormulaIngredients().removeIf(s -> s.getId().equals(formulaIngredientId));
            return formulaRepository.save(formula);
        }).orElseThrow(() -> new RuntimeException("Formula does not exist"));
    }

    /**
     * Retrieves a FormulaIngredient by its ID from an existing Formula.
     *
     * @param formulaID The ID of the Formula where the ingredient is located.
     * @param formulaIngredientId The ID of the FormulaIngredient to be retrieved.
     * @return The retrieved FormulaIngredient if it exists, or null if not found.
     */
    @Override
    public Optional<FormulaIngredient> getFormulaIngredientFromFormula(Long formulaID, Long formulaIngredientId) {
        final Optional<Formula> optionalFormula = formulaRepository.findById(formulaID);
        return optionalFormula
                .map(formula -> formula.getFormulaIngredients().stream()
                        .filter(formulaIngredient -> Objects.equals(formulaIngredient.getId(), formulaIngredientId)).findFirst())
                .orElseThrow(() -> new RuntimeException("Formula does not exist"));
    }

    /**
     * Checks if a FormulaIngredient exists in the database by its ID.
     *
     * @param id The ID of the FormulaIngredient to check.
     * @return true if the FormulaIngredient exists, false otherwise.
     */
    @Override
    public boolean isFormulaIngredientExists(Long id) {
        return formulaIngredientRepository.existsById(id);
    }

}
