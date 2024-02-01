package com.amadeuscam.perfumir_api.controllers;

import com.amadeuscam.perfumir_api.dto.OlfactiveFamiliesDto;
import com.amadeuscam.perfumir_api.entities.OlfactiveFamilies;
import com.amadeuscam.perfumir_api.enums.OlfativeFamily;
import com.amadeuscam.perfumir_api.mappers.Maper;
import com.amadeuscam.perfumir_api.mappers.impl.OlfactiveFamiliesMapper;
import com.amadeuscam.perfumir_api.services.OlfativeFamiliesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/olfative-families")
@RequiredArgsConstructor
public class OlfativeFamiliesController {

    private final OlfativeFamiliesService olfativeFamiliesService;
    private final OlfactiveFamiliesMapper olfactiveFamiliesMapper;


    @GetMapping(path = "/{id}")
    public ResponseEntity<Set<OlfactiveFamiliesDto>> getOlfativeFamilies(@PathVariable("id") Long id) {
        final Set<OlfactiveFamilies> olfactiveFamiliess = olfativeFamiliesService.getOlfactiveFamiliess(id);
        final Set<OlfactiveFamiliesDto> collect = olfactiveFamiliess.stream().map(olfactiveFamiliesMapper::mapTo).collect(Collectors.toSet());
        return new ResponseEntity<>(collect, HttpStatus.OK);
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
