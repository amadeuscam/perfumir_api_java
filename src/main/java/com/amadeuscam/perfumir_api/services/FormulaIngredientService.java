package com.amadeuscam.perfumir_api.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.amadeuscam.perfumir_api.entities.Formula;
import com.amadeuscam.perfumir_api.entities.FormulaIngredient;

public interface FormulaIngredientService {

    List<FormulaIngredient> findAllFormulaIngredient();

    Set<FormulaIngredient> findAllFormulaIngredientsByFormula(Long formulaID);

    FormulaIngredient addFormulaIngredientToFormula(FormulaIngredient formula, Long formulaID);

    void addFormulaIngredientsToFormula(List<FormulaIngredient> formulaIngredients, Long formulaID);

    FormulaIngredient updateFormulaIngredientFromFormula(FormulaIngredient formula, Long formulaID);

    Formula deleteFormulaIngredient(Long formulaID, Long formulaIngredientId);

    Optional<FormulaIngredient> getFormulaIngredientFromFormula(Long formulaID, Long formulaIngredientId);

    boolean isFormulaIngredientExists(Long id);

}
