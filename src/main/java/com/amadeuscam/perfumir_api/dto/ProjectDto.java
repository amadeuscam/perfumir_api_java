package com.amadeuscam.perfumir_api.dto;

import com.amadeuscam.perfumir_api.entities.Formula;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class ProjectDto {
    private Long id;
    private String Name;
    private Date created;
    private Date updated;
    private Set<FormulaDto> formulas;
}
