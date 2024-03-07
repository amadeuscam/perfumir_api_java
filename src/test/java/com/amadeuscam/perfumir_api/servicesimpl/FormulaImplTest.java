package com.amadeuscam.perfumir_api.servicesimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
import com.amadeuscam.perfumir_api.entities.Project;
import com.amadeuscam.perfumir_api.repository.FormulaRepository;
import com.amadeuscam.perfumir_api.repository.ProjectRepository;
import com.amadeuscam.perfumir_api.services.impl.FormulaServiceImpl;

@ExtendWith(MockitoExtension.class)
public class FormulaImplTest {

    @Mock
    private FormulaRepository formulaRepository;
    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private FormulaServiceImpl formulaServiceImpl;

    @Test
    public void testThatCanAddFormulaToProject() {
        // Prepare mock data
        Formula formula = new Formula();
        Project project = new Project();
        project.setId(1L);
        Optional<Project> optionalProject = Optional.of(project);

        // Mock repository behavior
        when(projectRepository.findById(1L)).thenReturn(optionalProject);
        when(formulaRepository.save(formula)).thenReturn(formula);

        // Call the service method
        Formula createdFormula = formulaServiceImpl.createFormula(formula, 1L);

        // Assertions
        assertEquals(formula, createdFormula);
        verify(projectRepository, times(1)).findById(1L);
        verify(formulaRepository, times(1)).save(formula);
    }

    @Test
    public void testThatCanUpdateFormulaToProject() {
        // Prepare mock data
        Formula formula = new Formula();
        Project project = new Project();
        project.setId(1L);
        Optional<Project> optionalProject = Optional.of(project);

        // Mock repository behavior
        when(projectRepository.findById(1L)).thenReturn(optionalProject);
        when(formulaRepository.save(formula)).thenReturn(formula);

        formula.setName("marine");

        // Call the service method
        Formula createdFormula = formulaServiceImpl.updateFormula(formula, 1L);

        // Assertions
        assertEquals(formula.getName(), createdFormula.getName());
        verify(projectRepository, times(1)).findById(1L);
        verify(formulaRepository, times(1)).save(formula);
    }

    @Test
    public void testCreateFormula_ProjectNotFound() {
        // Prepare mock data
        Formula formula = new Formula();

        // Mock repository behavior (project not found)
        when(projectRepository.findById(1L)).thenReturn(Optional.empty());

        // Call the service method (expect exception)
        assertThrows(RuntimeException.class, () -> formulaServiceImpl.createFormula(formula, 1L));

    }

    @Test
    public void getFormula_projectExistsAndFormulaExists_returnsFormula() {
        Long projectID = 1L;
        Long formulaID = 2L;
        Project project = new Project();
        Formula formula = new Formula();

        when(projectRepository.findById(projectID)).thenReturn(Optional.of(project));
        when(formulaRepository.findById(formulaID)).thenReturn(Optional.of(formula));

        Optional<Formula> retrievedFormula = formulaServiceImpl.getFormula(projectID, formulaID);

        assertEquals(Optional.of(formula), retrievedFormula);
    }

    @Test
    public void getFormulaProjectDoesNotExistThrowsRuntimeException() {
        Long projectID = 1L;
        Long formulaID = 2L;

        when(projectRepository.findById(projectID)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> formulaServiceImpl.getFormula(projectID, formulaID));
    }

    @Test
    public void deleteFormulaProjectExistsAndFormulaExistsRemovesFormulaAndReturnsProject() {
        Long projectId = 1L;
        Long formulaId = 2L;

        Project project = new Project();
        Formula formulaToRemove = new Formula();

        formulaToRemove.setId(formulaId);
        Set<Formula> formulas = new HashSet<>();

        formulas.add(formulaToRemove);
        project.setFormulas(formulas);

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(projectRepository.save(project)).thenReturn(project); // Optional in this test

        Project returnedProject = formulaServiceImpl.deleteFormula(projectId, formulaId);

        assertEquals(project, returnedProject);
        assertTrue(project.getFormulas().isEmpty());
    }

    @Test
    public void deleteFormulaProjectExistsAndFormulaNotFoundReturnsProject() {
        Long projectId = 1L;
        Long formulaId = 2L;
        Project project = new Project();
        Set<Formula> formulas = new HashSet<>();
        project.setFormulas(formulas);

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(projectRepository.save(project)).thenReturn(project);

        Project returnedProject = formulaServiceImpl.deleteFormula(projectId, formulaId);

        assertEquals(project, returnedProject);
        assertEquals(formulas, returnedProject.getFormulas());
    }

    @Test
    public void deleteFormulaProjectDoesNotExistThrowsRuntimeException() {
        Long projectId = 1L;
        Long formulaId = 2L;

        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> formulaServiceImpl.deleteFormula(projectId, formulaId));
    }

    @Test
    public void testThatFormulaExists() {

        Long existingId = 1L;
        when(formulaRepository.existsById(existingId)).thenReturn(true);

        boolean exists = formulaServiceImpl.isFormulaExists(existingId);

        assertTrue(exists);
    }

    @Test
    public void isFormulaExistsFormulaDoesNotExistReturnsFalse() {
        Long nonExistingId = 2L;
        when(formulaRepository.existsById(nonExistingId)).thenReturn(false);

        boolean exists = formulaServiceImpl.isFormulaExists(nonExistingId);

        assertFalse(exists);
    }

    @Test
    public void findAllFormulasReturnsAllFormulas() {
        List<Formula> expectedFormulas = new ArrayList<>();
        expectedFormulas.add(new Formula());
        expectedFormulas.add(new Formula());

        when(formulaRepository.findAll()).thenReturn(expectedFormulas);

        List<Formula> actualFormulas = formulaServiceImpl.findAllFormulas();

        assertEquals(expectedFormulas, actualFormulas);
    }

    @Test
    public void findAllFormulasReturnsEmptyListWhenNoFormulasExist() {
        List<Formula> expectedFormulas = new ArrayList<>();

        when(formulaRepository.findAll()).thenReturn(expectedFormulas);

        List<Formula> actualFormulas = formulaServiceImpl.findAllFormulas();

        assertEquals(expectedFormulas, actualFormulas);
    }

}
