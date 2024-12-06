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

    /**
     * Creates a new comment associated with the specified formula.
     * 
     * @param comment The comment to create.
     * @param formulaId The ID of the formula to associate the comment with.
     * @return The created comment.
     * @throws RuntimeException if the formula does not exist.
     */
    @Override
    public Comment createComment(Comment comment, Long formulaId) {
        Optional<Formula> formula = formulaRepository.findById(formulaId);

        if (formula.isPresent()) {
            comment.setFormula(formula.get());
            return commentRepository.save(comment);
        } else {
            throw new RuntimeException("Formula does not exist");
        }
    }

    /**
     * Updates an existing comment associated with the specified formula.
     * 
     * @param comment The comment to update.
     * @param formulaId The ID of the formula containing the comment to be updated.
     * @return The formula with the updated comment.
     * @throws RuntimeException if the formula or comment does not exist.
     */
    @Override
    public Formula updateComment(Comment comment, Long formulaId) {
        Optional<Formula> optionalFormula = formulaRepository.findById(formulaId);

        if (!optionalFormula.isPresent()) {
            throw new RuntimeException("Formula does not exist");
        }

        Formula formula = optionalFormula.get();
        for (Comment existingComment : formula.getComments()) {
            if (existingComment.getId().equals(comment.getId())) {
                // Update comment properties by copying from new comment
                existingComment.setBody(comment.getBody());

                return formulaRepository.save(formula);
            }
        }

        throw new RuntimeException("Comment does not exist in the given formula");
    }

    /**
     * Deletes a comment associated with the specified formula.
     * 
     * @param commentId The ID of the comment to delete.
     * @param formulaId The ID of the formula containing the comment to be deleted.
     * @return The formula from which the comment was removed.
     * @throws RuntimeException if the formula does not exist.
     */
    @Override
    public Formula deleteComment(Long commentId, Long formulaId) {
        Optional<Formula> formula = formulaRepository.findById(formulaId);

        return formula.map(formulaInside -> {
            formulaInside.getComments().removeIf(d -> d.getId().equals(commentId));
            return formulaRepository.save(formulaInside);
        }).orElseThrow(() -> new RuntimeException("Formula does not exist"));
    }

}