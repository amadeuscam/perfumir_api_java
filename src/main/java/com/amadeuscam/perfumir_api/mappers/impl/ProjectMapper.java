package com.amadeuscam.perfumir_api.mappers.impl;

import com.amadeuscam.perfumir_api.dto.ProjectDto;
import com.amadeuscam.perfumir_api.entities.Project;
import com.amadeuscam.perfumir_api.mappers.Maper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ProjectMapper implements Maper<Project, ProjectDto> {

    private final ModelMapper modelMapper;

    @Override
    public ProjectDto mapTo(Project project) {
        return modelMapper.map(project, ProjectDto.class);
    }

    @Override
    public Project mapFrom(ProjectDto projectDto) {
        return modelMapper.map(projectDto, Project.class);
    }
}
