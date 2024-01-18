package com.amadeuscam.perfumir_api.controllers;

import com.amadeuscam.perfumir_api.dto.DilutionDto;
import com.amadeuscam.perfumir_api.dto.IngredientDto;
import com.amadeuscam.perfumir_api.entities.Dilution;
import com.amadeuscam.perfumir_api.mappers.Maper;
import com.amadeuscam.perfumir_api.services.DilutionService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/dilutions")
@RequiredArgsConstructor
public class DilutionController {

    private final DilutionService dilutionService;
    private final Maper<Dilution, DilutionDto> dilutionMapper;

    @PostMapping
    public ResponseEntity<DilutionDto> createDilution(@RequestBody DilutionDto dilutionDto) {

        final Dilution dilution = dilutionMapper.mapFrom(dilutionDto);
        final Dilution dilutionSaved = dilutionService.createDilution(dilution);
        return new ResponseEntity<>(dilutionMapper.mapTo(dilutionSaved), HttpStatus.CREATED);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<DilutionDto> getDilution(@PathVariable Long id) {
        final Optional<Dilution> dilutionRecord = dilutionService.getDilution(id);
        return dilutionRecord.map(dilution -> {
            final DilutionDto dilutionDto = dilutionMapper.mapTo(dilution);
            return new ResponseEntity<>(dilutionDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public List<DilutionDto> getDilutions() {
        final List<Dilution> dilutions = dilutionService.getDilutions();
        return dilutions.stream().map(dilutionMapper::mapTo).collect(Collectors.toList());
    }

    @PutMapping
    public ResponseEntity<DilutionDto> updateIngredient(@RequestBody DilutionDto dilutionDto) {
        if (!dilutionService.isDilutionExists(dilutionDto.getId())) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        final Dilution dilution = dilutionMapper.mapFrom(dilutionDto);
        final Dilution dilutionSaved = dilutionService.updateDilution(dilution);
        return new ResponseEntity<>(dilutionMapper.mapTo(dilutionSaved), HttpStatus.OK);
    }


}
