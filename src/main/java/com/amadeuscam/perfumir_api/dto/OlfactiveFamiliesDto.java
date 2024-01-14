package com.amadeuscam.perfumir_api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OlfactiveFamiliesDto {
    private Long id;
    private String name;
}
