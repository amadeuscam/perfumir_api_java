package com.amadeuscam.perfumir_api.servicesimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.amadeuscam.perfumir_api.entities.Formula;
import com.amadeuscam.perfumir_api.entities.FormulaManagement;
import com.amadeuscam.perfumir_api.repository.FormulaManagementRepository;
import com.amadeuscam.perfumir_api.repository.FormulaRepository;
import com.amadeuscam.perfumir_api.services.impl.FormulaServiceImpl;

public class FormulaImplTest {

    @Mock
    private FormulaRepository formulaRepository;

    @Mock
    private FormulaManagementRepository fManagementRepository;

    @InjectMocks
    private FormulaServiceImpl formulaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAllFormulas() {
        List<Formula> formulas = Arrays.asList(new Formula(), new Formula());
        when(formulaRepository.findAll()).thenReturn(formulas);

        List<Formula> result = formulaService.findAllFormulas();

        assertEquals(formulas.size(), result.size());
        verify(formulaRepository, times(1)).findAll();
    }

    @Test
    void testFindAllFormulasByFormulaManagement() {
        Long formulaManagementId = 1L;
        FormulaManagement fManagement = new FormulaManagement();
        Set<Formula> formulas = new HashSet<>(Arrays.asList(new Formula(), new Formula()));
        fManagement.setFormulas(formulas);

        when(fManagementRepository.findById(formulaManagementId)).thenReturn(Optional.of(fManagement));

        Set<Formula> result = formulaService.findAllFormulasByFormulaManagement(formulaManagementId);

        assertEquals(formulas.size(), result.size());
        verify(fManagementRepository, times(1)).findById(formulaManagementId);
    }

    @Test
    void testCreateFormula() {
        Long formulaManagementId = 1L;
        Formula formula = new Formula();
        FormulaManagement fManagement = new FormulaManagement();

        when(fManagementRepository.findById(formulaManagementId)).thenReturn(Optional.of(fManagement));
        when(formulaRepository.save(formula)).thenReturn(formula);

        Formula result = formulaService.createFormula(formula, formulaManagementId);

        assertNotNull(result);
        verify(fManagementRepository, times(1)).findById(formulaManagementId);
        verify(formulaRepository, times(1)).save(formula);
    }

    @Test
    void testCreateFormulaThrowsExceptionWhenFormulaManagementNotFound() {
        Long formulaManagementId = 1L;
        Formula formula = new Formula();

        // Configuramos el mock para que devuelva un Optional vacío
        when(fManagementRepository.findById(formulaManagementId)).thenReturn(Optional.empty());

        // Verificamos que el método lance una excepción
        assertThrows(RuntimeException.class, () -> {
            formulaService.createFormula(formula, formulaManagementId);
        });

        verify(fManagementRepository, times(1)).findById(formulaManagementId);
        verify(formulaRepository, never()).save(formula); // Asegura que save no se llame
    }

    @Test
    void testUpdateFormula() {
        Long formulaManagementId = 1L;
        Formula formula = new Formula();
        FormulaManagement fManagement = new FormulaManagement();

        when(fManagementRepository.findById(formulaManagementId)).thenReturn(Optional.of(fManagement));
        when(formulaRepository.save(formula)).thenReturn(formula);

        Formula result = formulaService.updateFormula(formula, formulaManagementId);

        assertNotNull(result);
        verify(fManagementRepository, times(1)).findById(formulaManagementId);
        verify(formulaRepository, times(1)).save(formula);
    }

    @Test
    void testGetFormula() {
        Long formulaManagementId = 1L;
        Long formulaId = 2L;
        Formula formula = new Formula();
        FormulaManagement fManagement = new FormulaManagement();

        when(fManagementRepository.findById(formulaManagementId)).thenReturn(Optional.of(fManagement));
        when(formulaRepository.findById(formulaId)).thenReturn(Optional.of(formula));

        Optional<Formula> result = formulaService.getFormula(formulaManagementId, formulaId);

        assertTrue(result.isPresent());
        verify(fManagementRepository, times(1)).findById(formulaManagementId);
        verify(formulaRepository, times(1)).findById(formulaId);
    }

    @Test
    void testIsFormulaExists() {
        Long formulaId = 1L;

        when(formulaRepository.existsById(formulaId)).thenReturn(true);

        boolean result = formulaService.isFormulaExists(formulaId);

        assertTrue(result);
        verify(formulaRepository, times(1)).existsById(formulaId);
    }

    @Test
    void testDeleteFormula() {
        Long formulaManagementId = 1L;
        Long formulaId = 2L;

        FormulaManagement fManagement = new FormulaManagement();
        Formula formula = new Formula();

        formula.setId(formulaId);
        Set<Formula> formulas = new HashSet<>(Collections.singletonList(formula));
        fManagement.setFormulas(formulas);

        when(fManagementRepository.findById(formulaManagementId)).thenReturn(Optional.of(fManagement));
        when(fManagementRepository.save(fManagement)).thenReturn(fManagement);

        FormulaManagement result = formulaService.deleteFormula(formulaManagementId, formulaId);

        assertNotNull(result);
        assertTrue(result.getFormulas().isEmpty());
        verify(fManagementRepository, times(1)).findById(formulaManagementId);
        verify(fManagementRepository, times(1)).save(fManagement);
    }

}
