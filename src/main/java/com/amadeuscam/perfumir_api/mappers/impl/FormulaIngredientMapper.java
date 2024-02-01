package com.amadeuscam.perfumir_api.mappers.impl;

import com.amadeuscam.perfumir_api.dto.FormulaIngredientDto;
import com.amadeuscam.perfumir_api.entities.FormulaIngredient;
import com.amadeuscam.perfumir_api.mappers.Maper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class FormulaIngredientMapper implements Maper<FormulaIngredient, FormulaIngredientDto> {

    private final ModelMapper modelMapper;

    @Override
    public FormulaIngredientDto mapTo(FormulaIngredient formulaIngredient) {
        return modelMapper.map(formulaIngredient, FormulaIngredientDto.class);
    }

    @Override
    public FormulaIngredient mapFrom(FormulaIngredientDto formulaIngredientDto) {
        return modelMapper.map(formulaIngredientDto, FormulaIngredient.class);
    }
}
