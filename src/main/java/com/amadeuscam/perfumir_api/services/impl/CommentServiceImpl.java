package com.amadeuscam.perfumir_api.services.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.amadeuscam.perfumir_api.entities.Comment;
import com.amadeuscam.perfumir_api.entities.Formula;
import com.amadeuscam.perfumir_api.repository.CommentRepository;
import com.amadeuscam.perfumir_api.repository.FormulaRepository;
import com.amadeuscam.perfumir_api.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

    private final FormulaRepository formulaRepository;
    private final CommentRepository commentRepository;

    public CommentServiceImpl(FormulaRepository formulaRepository, CommentRepository commentRepository) {
        this.formulaRepository = formulaRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public Comment createComment(Comment comment, Long formulaId) {
        Optional<Formula> formula = formulaRepository.findById(formulaId);

        if (formula.isPresent()) {
            comment.setFormula(formula.get());
            commentRepository.save(comment);
        } else {
            throw new RuntimeException("Formula does not exist");
        }

        return comment;
    }

    @Override
    public Formula updateComment(Comment comment, Long formulaId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateComment'");
    }

    @Override
    public Formula deleteComment(Long commentId, Long formulaId) {
        Optional<Formula> formula = formulaRepository.findById(formulaId);

        return formula.map(formulaInside -> {
            formulaInside.getComments().removeIf(d -> d.getId().equals(commentId));
            return formulaRepository.save(formulaInside);
        }).orElseThrow(() -> new RuntimeException("Formula does not exist"));
    }

}
