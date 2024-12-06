package com.amadeuscam.perfumir_api.controllers;

import com.amadeuscam.perfumir_api.dto.IngredientDto;
import com.amadeuscam.perfumir_api.dto.OlfactiveFamiliesDto;
import com.amadeuscam.perfumir_api.entities.Ingredient;
import com.amadeuscam.perfumir_api.entities.OlfactiveFamilies;
import com.amadeuscam.perfumir_api.enums.OlfativeFamily;
import com.amadeuscam.perfumir_api.mappers.impl.IngredientMapper;
import com.amadeuscam.perfumir_api.mappers.impl.OlfactiveFamiliesMapper;
import com.amadeuscam.perfumir_api.services.OlfativeFamiliesService;
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
@RequestMapping("/api/v1/olfative-families")
@RequiredArgsConstructor
public class OlfativeFamiliesController {

    private final OlfativeFamiliesService olfativeFamiliesService;
    private final OlfactiveFamiliesMapper olfactiveFamiliesMapper;
    private final IngredientMapper ingredientMapper;


    @GetMapping(path = "{ingredientId}")
    public ResponseEntity<Set<OlfactiveFamiliesDto>> getOlfativeFamiliesFromIngredient(@PathVariable("ingredientId") Long ingredientId) {
        final Set<OlfactiveFamilies> olfactiveFamiliess = olfativeFamiliesService.getOlfactiveFamiliess(ingredientId);
        final Set<OlfactiveFamiliesDto> collect = olfactiveFamiliess.stream().map(olfactiveFamiliesMapper::mapTo).collect(Collectors.toSet());
        return new ResponseEntity<>(collect, HttpStatus.OK);
    }

    @GetMapping(path = "{ingredientId}/{olfativeId}")
    public ResponseEntity<OlfactiveFamiliesDto> getOlfativeFamilyFromIngredient(@PathVariable("ingredientId") Long ingredientId,
            @PathVariable("olfativeId") Long olfativeId) {
        Optional<OlfactiveFamilies> olfactiveFamiliesOptional = olfativeFamiliesService.getOlfactiveFamilies(olfativeId, ingredientId);
        return olfactiveFamiliesOptional.map(olfactiveFamilies -> new ResponseEntity<>(olfactiveFamiliesMapper.mapTo(olfactiveFamilies), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(path = "{ingredientId}")
    public ResponseEntity<OlfactiveFamiliesDto> saveOlfativeFamilyToIngredient(@RequestBody OlfactiveFamiliesDto olfactiveFamiliesDto,
            @PathVariable("ingredientId") Long ingredientId) {
        OlfactiveFamilies olfactiveFamilies = olfactiveFamiliesMapper.mapFrom(olfactiveFamiliesDto);
        OlfactiveFamilies olfactiveFamiliesCreated = olfativeFamiliesService.createOlfactiveFamilies(olfactiveFamilies, ingredientId);
        return new ResponseEntity<>(olfactiveFamiliesMapper.mapTo(olfactiveFamiliesCreated), HttpStatus.CREATED);
    }

    @PutMapping(path = "{ingredientId}")
    public ResponseEntity<OlfactiveFamiliesDto> updateOlfativeFamilyFromIngredient(@RequestBody OlfactiveFamiliesDto olfactiveFamiliesDto,
            @PathVariable("ingredientId") Long ingredientId) {
        OlfactiveFamilies olfactiveFamilies = olfactiveFamiliesMapper.mapFrom(olfactiveFamiliesDto);
        OlfactiveFamilies olfactiveFamiliesCreated = olfativeFamiliesService.updateOlfactiveFamilies(olfactiveFamilies, ingredientId);
        return new ResponseEntity<>(olfactiveFamiliesMapper.mapTo(olfactiveFamiliesCreated), HttpStatus.OK);
    }

    @DeleteMapping(path = "{ingredientId}/{olfativeId}")
    @Secured("ADMIN")
    public ResponseEntity<IngredientDto> deleteOlfativeFamilyFromIngredient(@PathVariable("ingredientId") Long ingredientId,
            @PathVariable("olfativeId") Long olfativeId) {
        Ingredient ingredient = olfativeFamiliesService.deleteOlfactiveFamilies(olfativeId, ingredientId);
        return new ResponseEntity<>(ingredientMapper.mapTo(ingredient), HttpStatus.OK);
    }


    @RequestMapping("/families")
    public ResponseEntity<List<OlfativeFamily>> days() {
        return new ResponseEntity<>(Arrays.asList(OlfativeFamily.values()), HttpStatus.OK);
    }

    @GetMapping(path = "/unique")
    public List<String> getUniqueOlfativeFamilies() {
        return olfativeFamiliesService.getUniqueOlfactiveFamilies();
    }
}
