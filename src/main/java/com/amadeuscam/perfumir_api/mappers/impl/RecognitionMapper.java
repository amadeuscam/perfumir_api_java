package com.amadeuscam.perfumir_api.mappers.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.amadeuscam.perfumir_api.dto.RecognitionDto;
import com.amadeuscam.perfumir_api.entities.Recognition;
import com.amadeuscam.perfumir_api.mappers.Maper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class RecognitionMapper implements Maper<Recognition, RecognitionDto> {

    private final ModelMapper modelMapper;

    @Override
    public RecognitionDto mapTo(Recognition recognition) {
        return modelMapper.map(recognition, RecognitionDto.class);
    }

    @Override
    public Recognition mapFrom(RecognitionDto recognitionDto) {
        return modelMapper.map(recognitionDto, Recognition.class);
    }

}
