package com.amadeuscam.perfumir_api.mappers.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.amadeuscam.perfumir_api.dto.CommentDto;
import com.amadeuscam.perfumir_api.entities.Comment;
import com.amadeuscam.perfumir_api.mappers.Maper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class CommentMapper implements Maper<Comment, CommentDto> {
    private final ModelMapper modelMapper;

    @Override
    public CommentDto mapTo(Comment comment) {
        return modelMapper.map(comment, CommentDto.class);
    }

    @Override
    public Comment mapFrom(CommentDto commentDto) {
        return modelMapper.map(commentDto, Comment.class);
    }
}
