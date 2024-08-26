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
public class ProjectDto {
    private Long id;
    private String Name;
    private Date created;
    private Date updated;
    private Set<FormulaManagementDto> formulasManagement;
}
