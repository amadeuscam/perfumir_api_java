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

    /**
     * This method saves a project in the database
     * 
     * @param project - The project details as an object
     * @return The saved project object
     */
    @Override
    public Project createProject(Project project) {

        if (!(project.getFormulasManagement() == null) && !project.getFormulasManagement().isEmpty()) {
            project.getFormulasManagement().forEach(d -> d.setProject(project));
        }
        return projectRepository.save(project);
    }

    /**
     * Updates a project in the database.
     * 
     * @param project - The updated project details
     * @return The updated project object
     */
    @Override
    public Project updateProject(Project project) {

        if (!(project.getFormulasManagement() == null) && !project.getFormulasManagement().isEmpty()) {
            project.getFormulasManagement().forEach(d -> d.setProject(project));
        }
        return projectRepository.save(project);
    }

    /**
     * Retrieves a single project by its ID in the database.
     * 
     * @param id - The unique identifier for the project you want to retrieve
     * @return An Optional object containing the retrieved project
     */

    @Override
    public Optional<Project> getProject(Long id) {
        return projectRepository.findById(id);
    }

    /**
     * Deletes a specific project from the database based on given project ID.
     * 
     * @param id - The unique identifier of the project that needs to be deleted
     * @return boolean indicating whether the deletion was successful or not
     */
    @Override
    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }

    @Override
    public boolean isProjectExists(Long id) {
        return projectRepository.existsById(id);
    }
}
