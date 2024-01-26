package com.amadeuscam.perfumir_api.services.impl;

import com.amadeuscam.perfumir_api.dto.DilutionCountDto;
import com.amadeuscam.perfumir_api.entities.Dilution;
import com.amadeuscam.perfumir_api.repository.DilutionRepository;
import com.amadeuscam.perfumir_api.services.DilutionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
@Transactional
public class DilutionServiceImpl implements DilutionService {

    private final DilutionRepository dilutionRepository;

    @Override
    public Dilution createDilution(Dilution dilution) {
        return dilutionRepository.save(dilution);
    }

    @Override
    public Dilution updateDilution(Dilution dilution) {
        return dilutionRepository.save(dilution);
    }

    @Override
    public Optional<Dilution> getDilution(Long id) {
        return dilutionRepository.findById(id);
    }

    @Override
    public List<Dilution> getDilutions() {
        return StreamSupport.stream(dilutionRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public List<DilutionCountDto> getDilutionsQuantities() {
        return dilutionRepository.getCountByQuantity();
    }


    @Override
    public boolean isDilutionExists(Long id) {
        return dilutionRepository.existsById(id);
    }
}
