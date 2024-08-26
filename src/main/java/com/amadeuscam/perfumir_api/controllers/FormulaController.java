package com.amadeuscam.perfumir_api.controllers;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amadeuscam.perfumir_api.dto.FormulaDto;
import com.amadeuscam.perfumir_api.dto.FormulaManagementDto;
import com.amadeuscam.perfumir_api.entities.Formula;
import com.amadeuscam.perfumir_api.entities.FormulaManagement;
import com.amadeuscam.perfumir_api.mappers.impl.FormulaManagementMapper;
import com.amadeuscam.perfumir_api.mappers.impl.FormulaMapper;
import com.amadeuscam.perfumir_api.services.FormulaService;
import com.amadeuscam.perfumir_api.services.ProjectService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/formulas")
@RequiredArgsConstructor
@Slf4j
public class FormulaController {

    private final FormulaService formulaService;
    private final ProjectService projectService;
    private final FormulaMapper formulaMapper;
    private final FormulaManagementMapper formulaManagementMapper;

    @GetMapping(value = "{fmanagement_id}/{formula_id}")
    public ResponseEntity<FormulaDto> getFormulaFromProject(
            @PathVariable("fmanagement_id") Long fmanagement_id,
            @PathVariable("formula_id") Long formula_id) {
        final Optional<Formula> formula = formulaService.getFormula(fmanagement_id, formula_id);

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

    @GetMapping(value = "formulas-per-formula-management/{id}")
    public Set<FormulaDto> getALlFormulasPerFormulaManagement(@PathVariable("id") Long id) {
        final Set<Formula> allFormulasByFormulaManagement = formulaService.findAllFormulasByFormulaManagement(id);
        return allFormulasByFormulaManagement.stream().map(formulaMapper::mapTo).collect(Collectors.toSet());
    }

    @PostMapping(value = "add/{fmanagement_id}")
    public ResponseEntity<FormulaDto> saveFormulaToProject(@PathVariable("fmanagement_id") Long fmanagement_id,
            @RequestBody FormulaDto formulaDto) {
        log.warn(fmanagement_id.toString());
        log.warn(FormulaDto.class.toString());
        // log.warn(formulaDto);
        System.out.println(formulaDto);
        final Formula formula = formulaMapper.mapFrom(formulaDto);
        final Formula savedFormula = formulaService.createFormula(formula, fmanagement_id);
        return new ResponseEntity<>(formulaMapper.mapTo(savedFormula), HttpStatus.CREATED);

    }

    @PutMapping(value = "update/{fmanagement_id}")
    public ResponseEntity<FormulaDto> updateFormulaFromProject(@PathVariable("fmanagement_id") Long fmanagement_id,
            @RequestBody FormulaDto formulaDto) {
        if (!formulaService.isFormulaExists(formulaDto.getId()) || !projectService.isProjectExists(fmanagement_id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        final Formula formula = formulaMapper.mapFrom(formulaDto);
        final Formula savedFormula = formulaService.createFormula(formula, fmanagement_id);
        return new ResponseEntity<>(formulaMapper.mapTo(savedFormula), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "{fmanagement_id}/{formulaId}")
    @Secured("ADMIN")
    public ResponseEntity<FormulaManagementDto> deleteFormula(@PathVariable("fmanagement_id") Long fmanagement_id,
            @PathVariable("formulaId") Long formulaId) {

        System.out.println(fmanagement_id);
        System.out.println(formulaId);
        System.out.println(formulaService.isFormulaExists(formulaId));
        System.out.println(projectService.isProjectExists(fmanagement_id));
        // !projectService.isProjectExists(fmanagement_id) queda por implemetar esto para fmanagement no para project
        if (!formulaService.isFormulaExists(formulaId) ) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        final FormulaManagement fManagement = formulaService.deleteFormula(fmanagement_id, formulaId);
        return new ResponseEntity<>(formulaManagementMapper.mapTo(fManagement), HttpStatus.OK);
    }
}
