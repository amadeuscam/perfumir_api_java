package com.amadeuscam.perfumir_api.controllers;

import com.amadeuscam.perfumir_api.dto.FormulaDto;
import com.amadeuscam.perfumir_api.dto.FormulaIngredientDto;
import com.amadeuscam.perfumir_api.entities.Formula;
import com.amadeuscam.perfumir_api.entities.FormulaIngredient;
import com.amadeuscam.perfumir_api.mappers.impl.FormulaIngredientMapper;
import com.amadeuscam.perfumir_api.mappers.impl.FormulaMapper;
import com.amadeuscam.perfumir_api.services.FormulaIngredientService;
import com.amadeuscam.perfumir_api.services.FormulaService;
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
@RequestMapping("/api/v1/formula-ingredient")
@RequiredArgsConstructor
public class FormulaIngredientController {

    private final FormulaIngredientMapper formulaIngredientMapper;
    private final FormulaMapper formulaMapper;
    private final FormulaService formulaService;
    private final FormulaIngredientService formulaIngredientService;

    @GetMapping(value = "{formulaId}/{ingredientId}")
    public ResponseEntity<FormulaIngredientDto> getIngredientFromFormula(@PathVariable("formulaId") Long formulaId, @PathVariable("ingredientId") Long ingredientId) {
        final Optional<FormulaIngredient> ingredientFromFormula = formulaIngredientService.getFormulaIngredientFromFormula(formulaId, ingredientId);
        return ingredientFromFormula.map(ingredient -> {
            final FormulaIngredientDto ingredientDto = formulaIngredientMapper.mapTo(ingredient);
            return new ResponseEntity<>(ingredientDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public List<FormulaIngredientDto> getAllIngredientsFormula() {
        final List<FormulaIngredient> allFormulaIngredient = formulaIngredientService.findAllFormulaIngredient();
        return allFormulaIngredient.stream().map(formulaIngredientMapper::mapTo).collect(Collectors.toList());
    }

    @GetMapping(value = "{formulaId}")
    public List<FormulaIngredientDto> getAllIngredientFromFormula(@PathVariable("formulaId") Long formulaId) {
        final Set<FormulaIngredient> allFormulaIngredientsByFormula = formulaIngredientService.findAllFormulaIngredientsByFormula(formulaId);
        return allFormulaIngredientsByFormula.stream().map(formulaIngredientMapper::mapTo).collect(Collectors.toList());
    }

    @PostMapping(value = "{formulaId}")
    public ResponseEntity<FormulaIngredientDto> addIngredientToFormula(@PathVariable("formulaId") Long formulaId, @RequestBody FormulaIngredientDto formulaIngredientDto) {
        final FormulaIngredient formulaIngredient = formulaIngredientMapper.mapFrom(formulaIngredientDto);
        final FormulaIngredient toFormula = formulaIngredientService.addFormulaIngredientToFormula(formulaIngredient, formulaId);
        return new ResponseEntity<>(formulaIngredientMapper.mapTo(toFormula), HttpStatus.OK);
    }

    @PutMapping(value = "{formulaId}")
    public ResponseEntity<FormulaIngredientDto> updateIngredientFromFormula(@PathVariable("formulaId") Long formulaId, @RequestBody FormulaIngredientDto formulaIngredientDto) {
        final FormulaIngredient formulaIngredient = formulaIngredientMapper.mapFrom(formulaIngredientDto);
        if (!formulaService.isFormulaExists(formulaId) || !formulaIngredientService.isFormulaIngredientExists(formulaIngredientDto.getId())) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        final FormulaIngredient toFormula = formulaIngredientService.updateFormulaIngredientFromFormula(formulaIngredient, formulaId);
        return new ResponseEntity<>(formulaIngredientMapper.mapTo(toFormula), HttpStatus.OK);
    }

    @DeleteMapping("{formulaId}/{ingredientId}")
    @Secured("ADMIN")
    public ResponseEntity<FormulaDto> deleteIngredientFromFormula(@PathVariable("formulaId") Long formulaId, @PathVariable("ingredientId") Long ingredientId) {

        if (!formulaService.isFormulaExists(formulaId) || !formulaIngredientService.isFormulaIngredientExists(ingredientId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        final Formula formula = formulaIngredientService.deleteFormulaIngredient(formulaId, ingredientId);
        return new ResponseEntity<>(formulaMapper.mapTo(formula), HttpStatus.NOT_FOUND);

    }
}
