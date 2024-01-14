package com.amadeuscam.perfumir_api.mappers.impl;

import com.amadeuscam.perfumir_api.dto.IngredientDto;
import com.amadeuscam.perfumir_api.entities.Ingredient;
import com.amadeuscam.perfumir_api.mappers.Maper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class IngredientMapper implements Maper<Ingredient, IngredientDto> {

    private final ModelMapper modelMapper;


    @Override
    public IngredientDto mapTo(Ingredient ingredient) {
        return modelMapper.map(ingredient, IngredientDto.class);
    }

    @Override
    public Ingredient mapFrom(IngredientDto ingredientDto) {
        return modelMapper.map(ingredientDto, Ingredient.class);
    }
}
