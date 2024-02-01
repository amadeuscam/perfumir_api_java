package com.amadeuscam.perfumir_api.controllers;

import com.amadeuscam.perfumir_api.dto.FormulaDto;
import com.amadeuscam.perfumir_api.dto.ProjectDto;
import com.amadeuscam.perfumir_api.entities.Formula;
import com.amadeuscam.perfumir_api.entities.Project;
import com.amadeuscam.perfumir_api.mappers.impl.FormulaMapper;
import com.amadeuscam.perfumir_api.mappers.impl.ProjectMapper;
import com.amadeuscam.perfumir_api.services.FormulaService;
import com.amadeuscam.perfumir_api.services.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/formulas")
@RequiredArgsConstructor
public class FormulaController {

    private final FormulaService formulaService;
    private final ProjectService projectService;
    private final FormulaMapper formulaMapper;
    private final ProjectMapper projectMapper;

    @GetMapping(value = "{project_id}/{formula_id}")
    public ResponseEntity<FormulaDto> getFormulaFromProject(
            @PathVariable("project_id") Long project_id,
            @PathVariable("formula_id") Long formula_id) {
        final Optional<Formula> formula = formulaService.getFormula(project_id, formula_id);

        return formula.map(formula1 -> {
            final FormulaDto formulaDto = formulaMapper.mapTo(formula1);
            return new ResponseEntity<>(formulaDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @GetMapping
    public List<FormulaDto> getALlFormulas() {
        final List<Formula> allFormulas = formulaService.findAllFormulas();
        return allFormulas.stream().map(formulaMapper::mapTo).collect(Collectors.toList());
    }

    @GetMapping(value = "formulas-per-project/{id}")
    public Set<FormulaDto> getALlFormulasPerProject(@PathVariable("id") Long id) {
        final Set<Formula> allFormulasByProject = formulaService.findAllFormulasByProject(id);
        return allFormulasByProject.stream().map(formulaMapper::mapTo).collect(Collectors.toSet());
    }

    @PostMapping(value = "add/{project_id}")
    public ResponseEntity<FormulaDto> saveFormulaToProject(@PathVariable("project_id") Long project_id, @RequestBody FormulaDto formulaDto) {
        final Formula formula = formulaMapper.mapFrom(formulaDto);
        final Formula savedFormula = formulaService.createFormula(formula, project_id);
        return new ResponseEntity<>(formulaMapper.mapTo(savedFormula), HttpStatus.CREATED);

    }

    @PutMapping(value = "update/{project_id}")
    public ResponseEntity<FormulaDto> updateFormulaFromProject(@PathVariable("project_id") Long project_id, @RequestBody FormulaDto formulaDto) {
        if (!formulaService.isFormulaExists(formulaDto.getId()) || !projectService.isProjectExists(project_id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        final Formula formula = formulaMapper.mapFrom(formulaDto);
        final Formula savedFormula = formulaService.createFormula(formula, project_id);
        return new ResponseEntity<>(formulaMapper.mapTo(savedFormula), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "{projectID}/{formulaId}")
    @Secured("ADMIN")
    public ResponseEntity<ProjectDto> deleteFormula(@PathVariable("projectID") Long projectID, @PathVariable("formulaId") Long formulaId) {
        if (!formulaService.isFormulaExists(formulaId) || !projectService.isProjectExists(projectID)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        final Project project = formulaService.deleteFormula(projectID, formulaId);
        return new ResponseEntity<>(projectMapper.mapTo(project), HttpStatus.OK);
    }
}
