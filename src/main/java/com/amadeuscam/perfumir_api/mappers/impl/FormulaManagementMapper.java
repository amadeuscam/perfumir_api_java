package com.amadeuscam.perfumir_api.mappers.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.amadeuscam.perfumir_api.dto.FormulaManagementDto;
import com.amadeuscam.perfumir_api.entities.FormulaManagement;
import com.amadeuscam.perfumir_api.mappers.Maper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class FormulaManagementMapper implements Maper<FormulaManagement, FormulaManagementDto> {

    private final ModelMapper modelMapper;

    @Override
    public FormulaManagementDto mapTo(FormulaManagement fManagement) {
        return modelMapper.map(fManagement, FormulaManagementDto.class);
    }

    @Override
    public FormulaManagement mapFrom(FormulaManagementDto formulaManagementDto) {
        return modelMapper.map(formulaManagementDto, FormulaManagement.class);
    }

}
