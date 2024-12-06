package com.amadeuscam.perfumir_api.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amadeuscam.perfumir_api.dto.IngredientDto;
import com.amadeuscam.perfumir_api.entities.Ingredient;
import com.amadeuscam.perfumir_api.mappers.impl.IngredientMapper;
import com.amadeuscam.perfumir_api.services.IngredientService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/ingredients")
@RequiredArgsConstructor
public class IngredientController {

    private final IngredientService ingredientService;
    private final IngredientMapper ingredientMapper;

    private static Logger logger = LoggerFactory.getLogger(IngredientController.class);

    @PostMapping
    public ResponseEntity<IngredientDto> createIngredient(@RequestBody IngredientDto ingredientDto) {
        System.out.println(ingredientDto);
        final Ingredient ingredient = ingredientMapper.mapFrom(ingredientDto);
        final Ingredient savedIngredient = ingredientService.createIngredient(ingredient);
        return new ResponseEntity<>(ingredientMapper.mapTo(savedIngredient), HttpStatus.CREATED);
    }

    @GetMapping
    public List<IngredientDto> getIngredients() {

        logger.info("hola desde info");
        logger.debug("hola desde debug");
        logger.warn("hola desde warm");
        logger.error("hola desde error");
        final List<Ingredient> ingredientList = ingredientService.findAll();
        return ingredientList.stream().map(ingredientMapper::mapTo).collect(Collectors.toList());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<IngredientDto> getIngredient(@PathVariable("id") Long id) {
        final Optional<Ingredient> ingredientRecord = ingredientService.getIngredient(id);
        return ingredientRecord.map(ingredient -> {
            final IngredientDto ingredientDto = ingredientMapper.mapTo(ingredient);
            return new ResponseEntity<>(ingredientDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping
    public ResponseEntity<IngredientDto> updateIngredient(@RequestBody IngredientDto ingredientDto) {
        if (!ingredientService.isExists(ingredientDto.getId())) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        final Ingredient ingredient = ingredientMapper.mapFrom(ingredientDto);
        final Ingredient ingredientRecord = ingredientService.updateIngredient(ingredient);
        return new ResponseEntity<>(ingredientMapper.mapTo(ingredientRecord), HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<IngredientDto> partialUpdateIngredient(@RequestBody IngredientDto ingredientDto) {
        if (!ingredientService.isExists(ingredientDto.getId())) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        final Ingredient ingredient = ingredientMapper.mapFrom(ingredientDto);
        final Ingredient ingredientRecord = ingredientService.partialUpdate(ingredient);
        return new ResponseEntity<>(ingredientMapper.mapTo(ingredientRecord), HttpStatus.OK);
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
