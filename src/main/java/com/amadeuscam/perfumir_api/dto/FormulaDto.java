package com.amadeuscam.perfumir_api.dto;

import java.util.Date;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class FormulaDto {
    private Long id;
    private String status;
    private String version;
    private Date created;
    private Date updated;

    private Set<FormulaIngredientDto> formulaIngredients;
    private Set<CommentDto> comments;
}
