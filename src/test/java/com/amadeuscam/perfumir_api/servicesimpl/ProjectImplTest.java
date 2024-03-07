package com.amadeuscam.perfumir_api.servicesimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.amadeuscam.perfumir_api.TestDataUtil;
import com.amadeuscam.perfumir_api.entities.Project;
import com.amadeuscam.perfumir_api.repository.ProjectRepository;
import com.amadeuscam.perfumir_api.services.impl.ProjectServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ProjectImplTest {

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectServiceImpl projectService;

    Project project;

    @BeforeEach
    void setUp() {
        project = TestDataUtil.createProject(null);
    }

    @Test
    public void testThatCanCreateProject() {
        project.setId(null);
        when(projectRepository.save(eq(project))).thenReturn(project);
        Project serviceProject = projectService.createProject(project);
        assertEquals(project, serviceProject);
    }

    @Test
    public void testThatCanReadProject() {
        when(projectRepository.findById(project.getId())).thenReturn(Optional.of(project));
        projectService.createProject(project);
        Optional<Project> projectOptional = projectService.getProject(1L);
        assertEquals(true, projectOptional.isPresent());
    }

    @Test
    public void testThatCanUpdateProject() {
        project.setName("marine");
        when(projectRepository.save(eq(project))).thenReturn(project);
        Project serviceProject = projectService.updateProject(project);
        assertEquals(project.getName(), serviceProject.getName());
    }

    @Test
    public void testThatCanDeleteProject() {
        projectService.deleteProject(project.getId());
        assertEquals(Optional.empty(), projectService.getProject(1L));

    }
}
