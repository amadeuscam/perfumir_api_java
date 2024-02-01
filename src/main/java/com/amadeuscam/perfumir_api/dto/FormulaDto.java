package com.amadeuscam.perfumir_api.dto;

import com.amadeuscam.perfumir_api.entities.FormulaIngredient;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FormulaDto {
    private Long id;
    private String name;
    private String status;
    private String version;
    private Date created;
    private Date updated;

    private Set<FormulaIngredientDto> formulaIngredients;
}
