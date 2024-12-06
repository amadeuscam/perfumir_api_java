package com.amadeuscam.perfumir_api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amadeuscam.perfumir_api.dto.CommentDto;
import com.amadeuscam.perfumir_api.entities.Comment;
import com.amadeuscam.perfumir_api.mappers.impl.CommentMapper;
import com.amadeuscam.perfumir_api.services.CommentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final CommentMapper commentMapper;

    @PostMapping("{formulaId}")
    public ResponseEntity<CommentDto> addCommentToFormula(@RequestBody CommentDto commentDto, @PathVariable("formulaId") Long formulaId) {
        System.out.println(commentDto);
        System.out.println(formulaId);
        Comment comment = commentMapper.mapFrom(commentDto);
        Comment commentResponse = commentService.createComment(comment, formulaId);
        return new ResponseEntity<>(commentMapper.mapTo(commentResponse), HttpStatus.CREATED);

    }

    @DeleteMapping("{formulaId}/{commentId}")
    public ResponseEntity<CommentDto> deleteCommentFromFormula(@PathVariable Long formulaId, @PathVariable Long commentId) {

        commentService.deleteComment(commentId, formulaId);
        return new ResponseEntity<>(HttpStatus.OK);

    }

}
