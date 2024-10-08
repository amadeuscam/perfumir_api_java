package com.amadeuscam.perfumir_api.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FormulaIngredientDto {

    private Long id;
    private String ingredientName;
    private String pyramidLevel;
    private BigDecimal amount;
    private Integer dilutionOfIngredient;
    private Integer alcohol;
    private Date created;
    private Date updated;
}
