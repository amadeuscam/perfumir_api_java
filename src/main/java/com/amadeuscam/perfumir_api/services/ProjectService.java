package com.amadeuscam.perfumir_api.services;

import com.amadeuscam.perfumir_api.entities.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectService {

    List<Project> findAllProjects();

    Project createProject(Project project);

    Project updateProject(Project project);

    Optional<Project> getProject(Long id);

    void deleteProject(Long id);

    boolean isProjectExists(Long id);
}
