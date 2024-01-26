package com.amadeuscam.perfumir_api.controllers;

import com.amadeuscam.perfumir_api.dto.DilutionCountDto;
import com.amadeuscam.perfumir_api.dto.DilutionDto;
import com.amadeuscam.perfumir_api.entities.Dilution;
import com.amadeuscam.perfumir_api.enums.OlfativeFamily;
import com.amadeuscam.perfumir_api.mappers.Maper;
import com.amadeuscam.perfumir_api.services.DilutionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/dilutions")
@RequiredArgsConstructor
public class DilutionController {

    private final DilutionService dilutionService;

    @GetMapping("/count-quantity")
    public List<DilutionCountDto> getDilutionsQuantities() {
        return dilutionService.getDilutionsQuantities();
    }

    @GetMapping("/dilution-list")
    public ResponseEntity<List<Integer>> dilutions() {
        return new ResponseEntity<>(Arrays.asList(100, 50, 20, 10, 5, 1), HttpStatus.OK);
    }


}
