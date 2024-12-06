package com.amadeuscam.perfumir_api.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.amadeuscam.perfumir_api.entities.FormulaManagement;
import com.amadeuscam.perfumir_api.entities.Project;
import com.amadeuscam.perfumir_api.repository.FormulaManagementRepository;
import com.amadeuscam.perfumir_api.repository.ProjectRepository;
import com.amadeuscam.perfumir_api.services.FormulaManagementService;

@Service
public class FormulaManagementSerrviceImpl implements FormulaManagementService {

    private final FormulaManagementRepository fManagementRepository;
    private final ProjectRepository projectRepository;

    public FormulaManagementSerrviceImpl(FormulaManagementRepository fManagementRepository,
            ProjectRepository projectRepository) {
        this.fManagementRepository = fManagementRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public List<FormulaManagement> findAllFormulaManagement() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAllFormulaManagement'");
    }

    @Override
    public FormulaManagement createFormulaManagement(FormulaManagement formulaManagement, Long projectId) {
        Optional<Project> projectOptional = projectRepository.findById(projectId);

        if (projectOptional.isPresent()) {
            formulaManagement.setProject(projectOptional.get());
        } else {
            new RuntimeException("Project does not exist");
        }

        return fManagementRepository.save(formulaManagement);
    }

    @Override
    public FormulaManagement updateFormulaManagement(FormulaManagement formulaManagement, Long projectId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateFormulaManagement'");
    }

    @Override
    public FormulaManagement getFormulaManagement(Long formulaId, Long projectId) {
        Optional<Project> projectOptional = projectRepository.findById(projectId);

        return projectOptional.map(project -> {
            
            return project.getFormulasManagement().stream().filter(fm -> fm.getId().equals(formulaId)).findFirst()
                    .get();
        }).orElseThrow(() -> new RuntimeException("Formula Management does not exist"));
    }

    @Override
    public Project deleteFormulaManagement(Long formulaId, Long projectId) {
        Optional<Project> projectOptional = projectRepository.findById(projectId);

        return projectOptional.map(project -> {
            project.getFormulasManagement().removeIf(fm -> fm.getId().equals(formulaId));
            return projectRepository.save(project);
        }).orElseThrow(() -> new RuntimeException("Formula Management does not exist"));
    }

}
