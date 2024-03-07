package com.amadeuscam.perfumir_api.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.amadeuscam.perfumir_api.entities.Formula;
import com.amadeuscam.perfumir_api.entities.Project;
import com.amadeuscam.perfumir_api.repository.FormulaRepository;
import com.amadeuscam.perfumir_api.repository.ProjectRepository;
import com.amadeuscam.perfumir_api.services.FormulaService;

@Service
public class FormulaServiceImpl implements FormulaService {

    private final FormulaRepository formulaRepository;

    private final ProjectRepository projectRepository;

    public FormulaServiceImpl(FormulaRepository formulaRepository, ProjectRepository projectRepository) {
        this.formulaRepository = formulaRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public List<Formula> findAllFormulas() {
        return formulaRepository.findAll();
    }

    @Override
    public Set<Formula> findAllFormulasByProject(Long projectID) {
        final Optional<Project> project = projectRepository.findById(projectID);
        return project.map(Project::getFormulas).orElseThrow(() -> new RuntimeException("Project does not exist"));

    }

    @Override
    public Formula createFormula(Formula formula, Long projectID) {
        final Optional<Project> project = projectRepository.findById(projectID);
        return project.map(project1 -> {
            formula.setProject(project1);
            return formulaRepository.save(formula);

        }).orElseThrow(() -> new RuntimeException("Project does not exist"));
    }

    @Override
    public Formula updateFormula(Formula formula, Long projectID) {
        final Optional<Project> project = projectRepository.findById(projectID);
        return project.map(project1 -> {
            formula.setProject(project1);
            return formulaRepository.save(formula);

        }).orElseThrow(() -> new RuntimeException("Project does not exist"));
    }

    @Override
    public Optional<Formula> getFormula(Long projectID, Long id) {
        final Optional<Project> project = projectRepository.findById(projectID);
        return project.map(project1 -> formulaRepository.findById(id))
                .orElseThrow(() -> new RuntimeException("Project does not exist"));
    }

    @Override
    public boolean isFormulaExists(Long id) {
        return formulaRepository.existsById(id);
    }

    @Override
    public Project deleteFormula(Long projectID, Long formulaId) {
        final Optional<Project> project = projectRepository.findById(projectID);
        return project.map(project1 -> {
            project1.getFormulas().removeIf(s -> s.getId().equals(formulaId));
            return projectRepository.save(project1);
        }).orElseThrow(() -> new RuntimeException("Project does not exist"));
    }
}
