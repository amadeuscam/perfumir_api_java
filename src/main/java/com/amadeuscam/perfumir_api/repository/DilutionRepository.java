package com.amadeuscam.perfumir_api.repository;

import com.amadeuscam.perfumir_api.dto.DilutionCountDto;
import com.amadeuscam.perfumir_api.entities.Dilution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DilutionRepository extends JpaRepository<Dilution, Long> {

    @Query("select new com.amadeuscam.perfumir_api.dto.DilutionCountDto(d.quantity,count(d) ) from Dilution d group by d.quantity")
    public List<DilutionCountDto> getCountByQuantity();
}

