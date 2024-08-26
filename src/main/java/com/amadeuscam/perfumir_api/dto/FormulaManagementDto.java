package com.amadeuscam.perfumir_api.dto;

import java.util.Date;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FormulaManagementDto {
    private Long id;
    private String name;
    private String status;
    private String version;
    private Date created;
    private Date updated;

    private Set<FormulaDto> formulas;
}
