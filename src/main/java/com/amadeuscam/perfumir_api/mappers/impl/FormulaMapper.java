package com.amadeuscam.perfumir_api.mappers.impl;

import com.amadeuscam.perfumir_api.dto.FormulaDto;
import com.amadeuscam.perfumir_api.entities.Formula;
import com.amadeuscam.perfumir_api.mappers.Maper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class FormulaMapper implements Maper<Formula, FormulaDto> {

    private final ModelMapper modelMapper;

    @Override
    public FormulaDto mapTo(Formula formula) {
        return modelMapper.map(formula, FormulaDto.class);
    }

    @Override
    public Formula mapFrom(FormulaDto formulaDto) {
        return modelMapper.map(formulaDto, Formula.class);
    }
}
