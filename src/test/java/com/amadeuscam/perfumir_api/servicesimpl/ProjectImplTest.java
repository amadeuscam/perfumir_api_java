package com.amadeuscam.perfumir_api.servicesimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.amadeuscam.perfumir_api.entities.Project;
import com.amadeuscam.perfumir_api.repository.ProjectRepository;
import com.amadeuscam.perfumir_api.services.impl.ProjectServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ProjectImplTest {

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectServiceImpl projectService;

    @Test
    public void testThatCanCreateProject() {
        Project project = new Project();

        when(projectRepository.save(project)).thenReturn(project);

        Project result = projectService.createProject(project);
        assertNotNull(result);
        verify(projectRepository, times(1)).save(project);
    }

    @Test
    public void testThatCanReadProject() {
        Long projectID = 1l;
        Project project = new Project();
        when(projectRepository.findById(projectID)).thenReturn(Optional.of(project));
        Optional<Project> result = projectService.getProject(projectID);
        assertTrue(result.isPresent());
        assertEquals(project, result.get());
        verify(projectRepository, times(1)).findById(projectID);
    }

    @Test
    public void testThatCanUpdateProject() {
        Project project = new Project();

        when(projectRepository.save(project)).thenReturn(project);

        Project result = projectService.updateProject(project);
        assertNotNull(result);
        verify(projectRepository, times(1)).save(project);
    }

    @Test
    public void testThatCanDeleteProject() {
        Long projectID = 1l;

        doNothing().when(projectRepository).deleteById(projectID);

        projectService.deleteProject(projectID);
        verify(projectRepository, times(1)).deleteById(projectID);

    }
}
