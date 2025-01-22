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

    public FormulaManagementSerrviceImpl(FormulaManagementRepository fManagementRepository, ProjectRepository projectRepository) {
        this.fManagementRepository = fManagementRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public List<FormulaManagement> findAllFormulaManagement() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAllFormulaManagement'");
    }

    /**
     * Creates a new formula management tied to an existing project via assignment of the latter
     * {@link Project}'s foreign key to the former's project.
     *
     * @param formulaManagement {@link FormulaManagement} instance with formulas to be persisted under
     *        project jurisdiction.
     * @param projectId Long ID ({@link #getId()} method reference) identifying foreign key project tied
     *        via {@link #setProject}.
     * @return {@link FormulaManagement} {@link FormulaManagement} retrieved from Spring Data
     *         {@link FormulaManagementRepository} persistence layer thanks to formula name matching.
     */
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

    /**
     * Updates a formula management object identified by its primary key ({@link #getId()})
     *
     * @param formulaManagement {@link FormulaManagement} identifier updated {@link FormulaManagement}
     *        instance previously retrieved from project context.
     */
    @Override
    public FormulaManagement updateFormulaManagement(FormulaManagement formulaManagement, Long projectId) {
        Optional<Project> projectOptional = projectRepository.findById(projectId);

        if (projectOptional.isPresent()) {
            Optional<FormulaManagement> fmWithIdOptional =
                    projectOptional.get().getFormulasManagement().stream().filter(fm -> fm.getId().equals(formulaManagement.getId())).findFirst();
            if (fmWithIdOptional.isPresent()) {
                fmWithIdOptional.get().setName(formulaManagement.getName());
                fmWithIdOptional.get().setStatus(formulaManagement.getStatus());
                fmWithIdOptional.get().setVersion(formulaManagement.getVersion());
                return fManagementRepository.save(fmWithIdOptional.get());
            } else {
                throw new RuntimeException("FormulaManagement does not exist");
            }

        } else {
            throw new RuntimeException("Project does not exist");
        }
    }

    /**
     * Retrieves given formula management object by its identification ({@link #getId()}) tied to
     * primary key {@link FormulaManagement#getProject}.
     *
     * @param formulaId ID ({@link FormulaManagement#getId()}) identifier sought.
     */
    @Override
    public FormulaManagement getFormulaManagement(Long formulaId, Long projectId) {
        Optional<Project> projectOptional = projectRepository.findById(projectId);

        return projectOptional.map(project -> {

            return project.getFormulasManagement().stream().filter(fm -> fm.getId().equals(formulaId)).findFirst().get();
        }).orElseThrow(() -> new RuntimeException("Formula Management does not exist"));
    }

    /**
     * Removes a formula from current project ({@link Project}).
     *
     * @param formulaId ID Retrieved formula from current project identified by
     *        ({@link FormulaManagement#getId()})
     */
    @Override
    public Project deleteFormulaManagement(Long formulaId, Long projectId) {
        Optional<Project> projectOptional = projectRepository.findById(projectId);
        return projectOptional.map(project -> {
            project.getFormulasManagement().removeIf(fm -> fm.getId().equals(formulaId));
            return projectRepository.save(project);
        }).orElseThrow(() -> new RuntimeException("Formula Management does not exist"));
    }

}
