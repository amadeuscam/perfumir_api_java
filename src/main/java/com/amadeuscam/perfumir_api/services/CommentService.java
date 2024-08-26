package com.amadeuscam.perfumir_api.services;

import com.amadeuscam.perfumir_api.entities.Comment;
import com.amadeuscam.perfumir_api.entities.Formula;

public interface CommentService {
    Comment createComment(Comment comment, Long formulaId);

    Formula updateComment(Comment comment, Long formulaId);

    Formula deleteComment(Long commentId, Long formulaId);
}
