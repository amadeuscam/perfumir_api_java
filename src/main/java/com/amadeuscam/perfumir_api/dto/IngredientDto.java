package com.amadeuscam.perfumir_api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IngredientDto {
    private Long id;
    private String name;
    private String casNumber;
    private String pyramidLevel;
    private String odorDescription;
    private String type;
    public Integer odorImpact;
    public Integer odorLife;
    private Set<DilutionDto> dilutions;
    private Set<OlfactiveFamiliesDto> olfactiveFamilies;
}
