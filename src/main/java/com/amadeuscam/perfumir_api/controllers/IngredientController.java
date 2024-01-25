package com.amadeuscam.perfumir_api.controllers;

import com.amadeuscam.perfumir_api.dto.IngredientDto;
import com.amadeuscam.perfumir_api.entities.Ingredient;
import com.amadeuscam.perfumir_api.mappers.Maper;
import com.amadeuscam.perfumir_api.repository.IngredientRepository;
import com.amadeuscam.perfumir_api.services.IngredientService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/ingredients")
@RequiredArgsConstructor
public class IngredientController {

    private final IngredientService ingredientService;
    private final Maper<Ingredient, IngredientDto> ingredientMaper;


    @PostMapping
    public ResponseEntity<IngredientDto> createIngredient(@RequestBody IngredientDto ingredientDto) {
        final Ingredient ingredient = ingredientMaper.mapFrom(ingredientDto);
        final Ingredient savedIngredient = ingredientService.createIngredient(ingredient);
        return new ResponseEntity<>(ingredientMaper.mapTo(savedIngredient), HttpStatus.CREATED);
    }

    @GetMapping
    public List<IngredientDto> getIngredients() {
        final List<Ingredient> ingredientList = ingredientService.findAll();
        return ingredientList.stream().map(ingredientMaper::mapTo).collect(Collectors.toList());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<IngredientDto> getIngredient(@PathVariable("id") Long id) {
        final Optional<Ingredient> ingredientRecord = ingredientService.getIngredient(id);
        return ingredientRecord.map(ingredient -> {
            final IngredientDto ingredientDto = ingredientMaper.mapTo(ingredient);
            return new ResponseEntity<>(ingredientDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping
    public ResponseEntity<IngredientDto> updateIngredient(@RequestBody IngredientDto ingredientDto) {
        if (!ingredientService.isExists(ingredientDto.getId())) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        final Ingredient ingredient = ingredientMaper.mapFrom(ingredientDto);
        final Ingredient ingredientRecord = ingredientService.updateIngredient(ingredient);
        return new ResponseEntity<>(ingredientMaper.mapTo(ingredientRecord), HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<IngredientDto> partialUpdateIngredient(@RequestBody IngredientDto ingredientDto) {
        if (!ingredientService.isExists(ingredientDto.getId())) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        final Ingredient ingredient = ingredientMaper.mapFrom(ingredientDto);
        final Ingredient ingredientRecord = ingredientService.partialUpdate(ingredient);
        return new ResponseEntity<>(ingredientMaper.mapTo(ingredientRecord), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    @Secured("ADMIN")
    public ResponseEntity deleteIngredient(@PathVariable("id") Long id) {
        if (!ingredientService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ingredientService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
