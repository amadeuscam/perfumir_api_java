package com.amadeuscam.perfumir_api.mappers.impl;

import com.amadeuscam.perfumir_api.dto.OlfactiveFamiliesDto;
import com.amadeuscam.perfumir_api.entities.OlfactiveFamilies;
import com.amadeuscam.perfumir_api.mappers.Maper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OlfactiveFamiliesMapper implements Maper<OlfactiveFamilies, OlfactiveFamiliesDto> {

    private final ModelMapper modelMapper;

    @Override
    public OlfactiveFamiliesDto mapTo(OlfactiveFamilies olfactiveFamilies) {
        return modelMapper.map(olfactiveFamilies, OlfactiveFamiliesDto.class);
    }

    @Override
    public OlfactiveFamilies mapFrom(OlfactiveFamiliesDto olfactiveFamiliesDto) {
        return modelMapper.map(olfactiveFamiliesDto, OlfactiveFamilies.class);
    }
}
