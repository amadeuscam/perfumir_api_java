package com.amadeuscam.perfumir_api.controllers;

import com.amadeuscam.perfumir_api.dto.IngredientDto;
import com.amadeuscam.perfumir_api.entities.Ingredient;
import com.amadeuscam.perfumir_api.mappers.Maper;
import com.amadeuscam.perfumir_api.services.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
}
