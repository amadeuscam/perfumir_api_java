package com.amadeuscam.perfumir_api.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amadeuscam.perfumir_api.entities.Project;
import com.amadeuscam.perfumir_api.repository.ProjectRepository;
import com.amadeuscam.perfumir_api.services.ProjectService;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public List<Project> findAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    public Project createProject(Project project) {

        if (!(project.getFormulasManagement() == null) && !project.getFormulasManagement().isEmpty()) {
            project.getFormulasManagement().forEach(d -> d.setProject(project));
        }
        return projectRepository.save(project);
    }

    @Override
    public Project updateProject(Project project) {

        if (!(project.getFormulasManagement() == null) && !project.getFormulasManagement().isEmpty()) {
            project.getFormulasManagement().forEach(d -> d.setProject(project));
        }
        return projectRepository.save(project);
    }

    @Override
    public Optional<Project> getProject(Long id) {
        return projectRepository.findById(id);
    }

    @Override
    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }

    @Override
    public boolean isProjectExists(Long id) {
        return projectRepository.existsById(id);
    }
}
