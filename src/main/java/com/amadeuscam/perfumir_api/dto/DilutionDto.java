package com.amadeuscam.perfumir_api.dto;

import com.amadeuscam.perfumir_api.entities.Ingredient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DilutionDto {
    private Long id;
    private Integer quantity;
}
