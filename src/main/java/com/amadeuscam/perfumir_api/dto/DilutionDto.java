package com.amadeuscam.perfumir_api.dto;

import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "The street name is required.")
    private Integer quantity;
}
