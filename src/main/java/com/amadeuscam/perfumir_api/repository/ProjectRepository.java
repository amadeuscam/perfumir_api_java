package com.amadeuscam.perfumir_api.repository;

import com.amadeuscam.perfumir_api.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
}
