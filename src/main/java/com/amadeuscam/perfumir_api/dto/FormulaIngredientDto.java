package com.amadeuscam.perfumir_api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.constraints.Digits;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

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
