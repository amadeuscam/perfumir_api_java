package com.amadeuscam.perfumir_api.mappers.impl;

import com.amadeuscam.perfumir_api.dto.DilutionDto;
import com.amadeuscam.perfumir_api.entities.Dilution;
import com.amadeuscam.perfumir_api.mappers.Maper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DilutionMapper implements Maper<Dilution, DilutionDto> {

    private ModelMapper modelMapper;

    @Override
    public DilutionDto mapTo(Dilution dilution) {
        return modelMapper.map(dilution, DilutionDto.class);
    }

    @Override
    public Dilution mapFrom(DilutionDto dilutionDto) {
        return modelMapper.map(dilutionDto, Dilution.class);
    }
}
