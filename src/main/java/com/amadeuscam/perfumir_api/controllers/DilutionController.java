package com.amadeuscam.perfumir_api.controllers;

import com.amadeuscam.perfumir_api.dto.DilutionCountDto;
import com.amadeuscam.perfumir_api.dto.DilutionDto;
import com.amadeuscam.perfumir_api.dto.IngredientDto;
import com.amadeuscam.perfumir_api.entities.Dilution;
import com.amadeuscam.perfumir_api.entities.Ingredient;
import com.amadeuscam.perfumir_api.enums.OlfativeFamily;
import com.amadeuscam.perfumir_api.mappers.Maper;
import com.amadeuscam.perfumir_api.mappers.impl.DilutionMapper;
import com.amadeuscam.perfumir_api.mappers.impl.IngredientMapper;
import com.amadeuscam.perfumir_api.services.DilutionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/dilutions")
@RequiredArgsConstructor
public class DilutionController {

    private final DilutionService dilutionService;
    private final DilutionMapper dilutionMapper;
    private final IngredientMapper ingredientMapper;


    @GetMapping(value = "{ingredientId}/{id}")
    public ResponseEntity<DilutionDto> getDilution(@PathVariable("id") Long id, @PathVariable("ingredientId") Long ingredientId) {
        Optional<Dilution> dilutionOptional = dilutionService.getDilution(id, ingredientId);
        return dilutionOptional.map(dilution -> {
            DilutionDto dilutionDto = dilutionMapper.mapTo(dilution);
            return new ResponseEntity<>(dilutionDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(value = "{ingredientId}")
    public List<DilutionDto> getAllDilutionForIngredient(@PathVariable("ingredientId") Long ingredientId) {
        Set<Dilution> dilutions = dilutionService.getDilutions(ingredientId);
        return dilutions.stream().map(dilutionMapper::mapTo).collect(Collectors.toList());

    }

    @PostMapping("{ingredientId}")
    public ResponseEntity<DilutionDto> addDilutionToIngredient(@RequestBody DilutionDto dilutionDto, @PathVariable("ingredientId") Long ingredientId) {
        Dilution dilution = dilutionMapper.mapFrom(dilutionDto);
        Dilution dilutionCreated = dilutionService.createDilution(dilution, ingredientId);
        return new ResponseEntity<>(dilutionMapper.mapTo(dilutionCreated), HttpStatus.CREATED);
    }

    @PutMapping("{ingredientId}")
    public ResponseEntity<DilutionDto> updateDilutionFromIngredient(@RequestBody DilutionDto dilutionDto, @PathVariable("ingredientId") Long ingredientId) {
        boolean dilutionExists = dilutionService.isDilutionExists(dilutionDto.getId(), ingredientId);
        if (!dilutionExists) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Dilution dilution = dilutionMapper.mapFrom(dilutionDto);
        Dilution dilutionCreated = dilutionService.updateDilution(dilution, ingredientId);
        return new ResponseEntity<>(dilutionMapper.mapTo(dilutionCreated), HttpStatus.OK);
    }

    @DeleteMapping("{ingredientId}/{id}")
    @Secured("ADMIN")
    public ResponseEntity<IngredientDto> deleteDilutionFromIngredient(@PathVariable("id") Long id, @PathVariable("ingredientId") Long ingredientId) {
        boolean dilutionExists = dilutionService.isDilutionExists(id, ingredientId);
        if (!dilutionExists) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Ingredient ingredient = dilutionService.deleteDilution(id, ingredientId);
        return new ResponseEntity<>(ingredientMapper.mapTo(ingredient), HttpStatus.OK);
    }

    @GetMapping("/count-quantity")
    public List<DilutionCountDto> getDilutionsQuantities() {
        return dilutionService.getDilutionsQuantities();
    }

    @GetMapping("/dilution-list")
    public ResponseEntity<List<Integer>> dilutions() {
        return new ResponseEntity<>(Arrays.asList(100, 50, 20, 10, 5, 1), HttpStatus.OK);
    }


}
