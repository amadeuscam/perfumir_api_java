package com.amadeuscam.perfumir_api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amadeuscam.perfumir_api.dto.FormulaManagementDto;
import com.amadeuscam.perfumir_api.entities.FormulaManagement;
import com.amadeuscam.perfumir_api.mappers.impl.FormulaManagementMapper;
import com.amadeuscam.perfumir_api.services.FormulaManagementService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/formula-management")
@RequiredArgsConstructor
@Slf4j
public class FormulaManagementController {
    private final FormulaManagementMapper formulaManagementMapper;
    private final FormulaManagementService formulaManagementService;

    @PostMapping(value = "add/{project_id}")
    public ResponseEntity<FormulaManagementDto> saveFormulaToProject(@PathVariable("project_id") Long project_id,
            @RequestBody FormulaManagementDto fManagementDto) {
        FormulaManagement fManagement = formulaManagementMapper.mapFrom(fManagementDto);
        FormulaManagement formulaManagement = formulaManagementService.createFormulaManagement(fManagement, project_id);
        return new ResponseEntity<>(formulaManagementMapper.mapTo(formulaManagement), HttpStatus.CREATED);

    }

    @GetMapping(value = "{formulaManagement_id}/{project_id}")
    public ResponseEntity<FormulaManagementDto> getFormulaManagement(
            @PathVariable Long formulaManagement_id,
            @PathVariable Long project_id) {

        FormulaManagement formulaManagement = formulaManagementService.getFormulaManagement(
                formulaManagement_id, project_id);

        return new ResponseEntity<>(formulaManagementMapper.mapTo(formulaManagement), HttpStatus.OK);

    }

    @DeleteMapping(value = "{project_id}/{formulaManagement_id}")
    public ResponseEntity<FormulaManagementDto> deleteFormulaManagement(
            @PathVariable Long formulaManagement_id,
            @PathVariable Long project_id) {
        
        formulaManagementService.deleteFormulaManagement(formulaManagement_id, project_id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

}
