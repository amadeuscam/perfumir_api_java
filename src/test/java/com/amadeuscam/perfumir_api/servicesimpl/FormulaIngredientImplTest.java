package com.amadeuscam.perfumir_api.servicesimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
    public void testThatFindAllIngredientsFormula() {
        List<FormulaIngredient> formulaINgredients = new ArrayList<>();
        FormulaIngredient formulaIngredient = new FormulaIngredient();
        formulaINgredients.add(formulaIngredient);

        when(formulaIngredientRepository.findAll()).thenReturn(formulaINgredients);

        List<FormulaIngredient> allFormulaIngredient = formulaIngredientServiceImpl.findAllFormulaIngredient();
        assertEquals(formulaIngredient, allFormulaIngredient.get(0));
    }

    @Test
    public void testThatAddIngredientsToFormula() {
        Long formulaId = 1L;

        Formula formula = new Formula();
        FormulaIngredient formulaIngredient = new FormulaIngredient();

        formula.setId(formulaId);

        when(formulaRepository.findById(formulaId)).thenReturn(Optional.of(formula));
        when(formulaIngredientRepository.save(formulaIngredient)).thenReturn(formulaIngredient);

        FormulaIngredient formulaIngredientToFormula = formulaIngredientServiceImpl
                .addFormulaIngredientToFormula(formulaIngredient, formulaId);
        assertEquals(formulaIngredient, formulaIngredientToFormula);

    }

    @Test
    public void testThatAddIngredientsToFormulaThatNotExists() {
        Long formulaId = 1L;
        Long IngredientFormulaId = 2L;

        FormulaIngredient formulaIngredient = new FormulaIngredient();
        formulaIngredient.setId(IngredientFormulaId);

        assertThrows(RuntimeException.class,
                () -> formulaIngredientServiceImpl.addFormulaIngredientToFormula(formulaIngredient, formulaId));
    }

    @Test
    public void testThatUpdateIngredientsToFormula() {
        Long formulaId = 1L;

        Formula formula = new Formula();
        FormulaIngredient formulaIngredient = new FormulaIngredient();

        formula.setId(formulaId);
        formulaIngredient.setId(2l);

        Set<FormulaIngredient> formulaIngredients = new HashSet<>();
        formulaIngredients.add(formulaIngredient);

        formula.setFormulaIngredients(formulaIngredients);

        when(formulaRepository.findById(formulaId)).thenReturn(Optional.of(formula));
        when(formulaIngredientRepository.save(formulaIngredient)).thenReturn(formulaIngredient);

        FormulaIngredient formulaIngredientToFormula = formulaIngredientServiceImpl
                .updateFormulaIngredientFromFormula(formulaIngredient, formulaId);

        assertEquals(formulaIngredient, formulaIngredientToFormula);

    }

    @Test
    public void testThatUpdateIngredientsToFormulaThatNotExists() {
        Long formulaId = 1L;
        Long IngredientFormulaId = 2L;

        FormulaIngredient formulaIngredient = new FormulaIngredient();
        formulaIngredient.setId(IngredientFormulaId);

        Formula formula = new Formula();
        formula.setId(formulaId);
        Set<FormulaIngredient> formulaIngredients = new HashSet<>();
        formula.setFormulaIngredients(formulaIngredients);

        when(formulaRepository.findById(formulaId)).thenReturn(Optional.of(formula));

        assertThrows(RuntimeException.class,
                () -> formulaIngredientServiceImpl.updateFormulaIngredientFromFormula(formulaIngredient, formulaId));
    }

    @Test
    public void testThatDeleteIngredientFromFormula() {
        Long formulaId = 1L;

        Formula formula = new Formula();
        FormulaIngredient formulaIngredient = new FormulaIngredient();

        formula.setId(formulaId);
        formulaIngredient.setId(2l);

        Set<FormulaIngredient> formulaIngredients = new HashSet<>();
        formulaIngredients.add(formulaIngredient);

        formula.setFormulaIngredients(formulaIngredients);

        when(formulaRepository.findById(formulaId)).thenReturn(Optional.of(formula));
        when(formulaRepository.save(formula)).thenReturn(formula);

        Formula deleteFormulaIngredient = formulaIngredientServiceImpl.deleteFormulaIngredient(formulaId, 2L);

        assertEquals(formula, deleteFormulaIngredient);
        assertEquals(formula.getFormulaIngredients().size(), 0);

    }

    @Test
    public void testThatDeleteIngredientFromFormulaThatNotExists() {
        Long formulaId = 1L;

        Formula formula = new Formula();
        FormulaIngredient formulaIngredient = new FormulaIngredient();

        formula.setId(formulaId);
        formulaIngredient.setId(2l);

        Set<FormulaIngredient> formulaIngredients = new HashSet<>();
        formulaIngredients.add(formulaIngredient);

        formula.setFormulaIngredients(formulaIngredients);

        when(formulaRepository.findById(formulaId)).thenReturn(Optional.of(formula));

        assertThrows(RuntimeException.class,
                () -> formulaIngredientServiceImpl.deleteFormulaIngredient(formulaId, 3L));

    }

    @Test
    public void testThatGetFormulaIngredientFromFormula() {
        Long formulaId = 1L;

        Formula formula = new Formula();
        FormulaIngredient formulaIngredient = new FormulaIngredient();

        formula.setId(formulaId);
        formulaIngredient.setId(2l);

        Set<FormulaIngredient> formulaIngredients = new HashSet<>();
        formulaIngredients.add(formulaIngredient);

        formula.setFormulaIngredients(formulaIngredients);

        when(formulaRepository.findById(formulaId)).thenReturn(Optional.of(formula));

        Optional<FormulaIngredient> formulaIngredientFromFormula = formulaIngredientServiceImpl
                .getFormulaIngredientFromFormula(formulaId, 2L);

        assertTrue(formulaIngredientFromFormula.isPresent());
        assertEquals(formulaIngredientFromFormula.get(), formulaIngredient);
    }

    @Test
    public void testThatGetFormulaIngredientThatNotExistsFromFormula() {
        Long formulaId = 1L;
        Formula formula = new Formula();

        formula.setId(formulaId);
        Set<FormulaIngredient> formulaIngredients = new HashSet<>();
        formula.setFormulaIngredients(formulaIngredients);

        when(formulaRepository.findById(formulaId)).thenReturn(Optional.of(formula));

        Optional<FormulaIngredient> formulaIngredientFromFormula = formulaIngredientServiceImpl
                .getFormulaIngredientFromFormula(formulaId, 2L);

        assertTrue(formulaIngredientFromFormula.isEmpty());
    }

    @Test
    public void testThatFindAllFormulaIngredientsByFormula() {
        Long formulaId = 1L;
        Long formulaIngredientId = 2L;

        Formula formula = new Formula();
        FormulaIngredient formulaIngredient = new FormulaIngredient();

        formula.setId(formulaId);
        formulaIngredient.setId(formulaIngredientId);

        Set<FormulaIngredient> formulaIngredients = new HashSet<>();
        formulaIngredients.add(formulaIngredient);

        formula.setFormulaIngredients(formulaIngredients);

        when(formulaRepository.findById(formulaId)).thenReturn(Optional.of(formula));

        Set<FormulaIngredient> allFormulaIngredientsByFormula = formulaIngredientServiceImpl
                .findAllFormulaIngredientsByFormula(formulaId);

        assertTrue(allFormulaIngredientsByFormula.size() == 1);
        assertEquals(formulaIngredient, allFormulaIngredientsByFormula.stream().findFirst().get());
    }

}
