package com.amadeuscam.perfumir_api.servicesimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.amadeuscam.perfumir_api.entities.Formula;
import com.amadeuscam.perfumir_api.entities.FormulaIngredient;
import com.amadeuscam.perfumir_api.repository.FormulaIngredientRepository;
import com.amadeuscam.perfumir_api.repository.FormulaRepository;
import com.amadeuscam.perfumir_api.services.impl.FormulaIngredientServiceImpl;

@ExtendWith(MockitoExtension.class)
public class FormulaIngredientImplTest {

    @Mock
    private FormulaIngredientRepository formulaIngredientRepository;
    @Mock
    private FormulaRepository formulaRepository;
    @InjectMocks
    private FormulaIngredientServiceImpl formulaIngredientServiceImpl;

    @Test
    public void testFindAllFormulaIngredient() {

        List<FormulaIngredient> ingredients = new ArrayList<>();
        when(formulaIngredientRepository.findAll()).thenReturn(ingredients);

        List<FormulaIngredient> result = formulaIngredientServiceImpl.findAllFormulaIngredient();
        assertNotNull(result);
        assertEquals(ingredients, result);
    }

    @Test
    public void testThatAddIngredientsToFormula() {
        Long formulaId = 1L;

        Formula formula = new Formula();
        FormulaIngredient ingredient = new FormulaIngredient();

        when(formulaRepository.findById(formulaId)).thenReturn(Optional.of(formula));
        when(formulaIngredientRepository.save(ingredient)).thenReturn(ingredient);

        FormulaIngredient result = formulaIngredientServiceImpl.addFormulaIngredientToFormula(ingredient, formulaId);
        assertNotNull(result);
        assertEquals(formula, result.getFormula());
        verify(formulaIngredientRepository, times(1)).save(ingredient);

    }

    @Test
    public void testThatAddIngredientsToFormulaThatNotExists() {
        Long formulaId = 1L;
        FormulaIngredient ingredient = new FormulaIngredient();

        when(formulaRepository.findById(formulaId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            formulaIngredientServiceImpl.addFormulaIngredientToFormula(ingredient, formulaId);
        });
        assertEquals("Formula does not exist", exception.getMessage());
    }

    @Test
    public void testThatUpdateIngredientsToFormula() {

        Long formulaId = 1L;

        Formula formula = new Formula();
        FormulaIngredient ingredient = new FormulaIngredient();
        ingredient.setId(1l);

        Set<FormulaIngredient> ingredients = new HashSet<>();
        ingredients.add(ingredient);
        formula.setFormulaIngredients(ingredients);

        when(formulaRepository.findById(formulaId)).thenReturn(Optional.of(formula));
        when(formulaIngredientRepository.save(ingredient)).thenReturn(ingredient);

        FormulaIngredient result = formulaIngredientServiceImpl.updateFormulaIngredientFromFormula(ingredient, formulaId);

        assertNotNull(result);
        assertEquals(ingredient, result);
        verify(formulaIngredientRepository, times(1)).save(ingredient);

    }

    @Test
    public void testThatUpdateIngredientsToFormulaThatNotExists() {
        Long formulaId = 1L;
        Formula formula = new Formula();

        FormulaIngredient ingredient = new FormulaIngredient();
        ingredient.setId(2L);

        when(formulaRepository.findById(formulaId)).thenReturn(Optional.of(formula));

        assertThrows(RuntimeException.class, () -> formulaIngredientServiceImpl.updateFormulaIngredientFromFormula(ingredient, formulaId));

        verify(formulaRepository, times(1)).findById(formulaId);
    }

    @Test
    public void testThatDeleteIngredientFromFormula() {
        Long formulaId = 1L;

        Formula formula = new Formula();
        FormulaIngredient ingredient = new FormulaIngredient();
        ingredient.setId(2l);

        Set<FormulaIngredient> ingredients = new HashSet<>();
        ingredients.add(ingredient);
        formula.setFormulaIngredients(ingredients);

        when(formulaRepository.findById(formulaId)).thenReturn(Optional.of(formula));
        when(formulaRepository.save(formula)).thenReturn(formula);

        Formula result = formulaIngredientServiceImpl.deleteFormulaIngredient(formulaId, 2L);
        assertNotNull(result);
        assertFalse(result.getFormulaIngredients().contains(ingredient));
        verify(formulaRepository, times(1)).save(formula);

    }

    @Test
    public void testThatDeleteIngredientFromFormulaThatNotExists() {
        Long formulaId = 1L;
        Long ingredientId = 2L;

        Formula formula = new Formula();
        Set<FormulaIngredient> ingredients = new HashSet<>();
        formula.setFormulaIngredients(ingredients);

        when(formulaRepository.findById(formulaId)).thenReturn(Optional.of(formula));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            formulaIngredientServiceImpl.deleteFormulaIngredient(formulaId, ingredientId);
        });

        assertEquals("FormulaIngredient Id does not found", exception.getMessage());

    }

    @Test
    public void testThatGetFormulaIngredientFromFormula() {
        Long formulaId = 1L;
        Long ingredientId = 2L;

        Formula formula = new Formula();
        FormulaIngredient ingredient = new FormulaIngredient();
        ingredient.setId(ingredientId);

        Set<FormulaIngredient> ingredients = new HashSet<>();
        ingredients.add(ingredient);

        formula.setFormulaIngredients(ingredients);

        when(formulaRepository.findById(formulaId)).thenReturn(Optional.of(formula));

        Optional<FormulaIngredient> result = formulaIngredientServiceImpl.getFormulaIngredientFromFormula(formulaId, ingredientId);

        assertTrue(result.isPresent());
        assertEquals(result.get(), ingredient);
        verify(formulaRepository, times(1)).findById(formulaId);
    }

    @Test
    public void testThatGetFormulaIngredientThatNotExistsFromFormula() {
        Long formulaId = 1L;
        Formula formula = new Formula();

        Set<FormulaIngredient> ingredients = new HashSet<>();
        formula.setFormulaIngredients(ingredients);

        when(formulaRepository.findById(formulaId)).thenReturn(Optional.of(formula));

        Optional<FormulaIngredient> result = formulaIngredientServiceImpl.getFormulaIngredientFromFormula(formulaId, 2L);

        assertTrue(result.isEmpty());
    }

    @Test
    public void testThatFindAllFormulaIngredientsByFormula() {
        Long formulaId = 1L;

        Formula formula = new Formula();

        Set<FormulaIngredient> ingredients = new HashSet<>();
        formula.setFormulaIngredients(ingredients);

        when(formulaRepository.findById(formulaId)).thenReturn(Optional.of(formula));

        Set<FormulaIngredient> result = formulaIngredientServiceImpl.findAllFormulaIngredientsByFormula(formulaId);

        assertNotNull(result);
        assertEquals(ingredients, result);
    }

    @Test
    public void testIsFormulaIngredientExists() {
        // Arrange
        Long ingredientId = 1L;
        when(formulaIngredientRepository.existsById(ingredientId)).thenReturn(true);

        // Act
        boolean exists = formulaIngredientServiceImpl.isFormulaIngredientExists(ingredientId);

        // Assert
        assertTrue(exists);
    }

}
