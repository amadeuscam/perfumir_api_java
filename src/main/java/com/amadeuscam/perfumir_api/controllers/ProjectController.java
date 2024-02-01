package com.amadeuscam.perfumir_api.controllers;

import com.amadeuscam.perfumir_api.dto.ProjectDto;
import com.amadeuscam.perfumir_api.entities.Project;
import com.amadeuscam.perfumir_api.mappers.impl.ProjectMapper;
import com.amadeuscam.perfumir_api.services.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    private final ProjectMapper projectMapper;


    @GetMapping
    public List<ProjectDto> getProjects() {
        final List<Project> allProjects = projectService.findAllProjects();
        return allProjects.stream().map(projectMapper::mapTo).collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<ProjectDto> saveProject(@RequestBody ProjectDto projectDto) {
        final Project project = projectMapper.mapFrom(projectDto);
        final Project savedProject = projectService.createProject(project);
        return new ResponseEntity<>(projectMapper.mapTo(savedProject), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ProjectDto> updateProject(@RequestBody ProjectDto projectDto) {
        final Project project = projectMapper.mapFrom(projectDto);
        if (!projectService.isProjectExists(project.getId())) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        final Project savedProject = projectService.updateProject(project);
        return new ResponseEntity<>(projectMapper.mapTo(savedProject), HttpStatus.CREATED);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<ProjectDto> getProject(@PathVariable("id") Long id) {
        final Optional<Project> projectRecord = projectService.getProject(id);

        return projectRecord.map(project -> {
            final ProjectDto projectDto = projectMapper.mapTo(project);
            return new ResponseEntity<>(projectDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(path = "/{id}")
    @Secured("ADMIN")
    public ResponseEntity deleteProject(@PathVariable("id") Long id) {
        if (!projectService.isProjectExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        projectService.deleteProject(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
