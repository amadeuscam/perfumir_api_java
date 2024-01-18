package com.amadeuscam.perfumir_api.servicesimpl;

import com.amadeuscam.perfumir_api.TestDataUtil;
import com.amadeuscam.perfumir_api.entities.Dilution;
import com.amadeuscam.perfumir_api.repository.DilutionRepository;
import com.amadeuscam.perfumir_api.services.impl.DilutionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
public class DilutionImplTest {

    @Mock
    private DilutionRepository dilutionRepository;
    @InjectMocks
    private DilutionServiceImpl dilutionService;

    private Dilution dilution;

    @BeforeEach
    void setUp() {
        dilution = TestDataUtil.createDilution();
    }

    @Test
    public void testThatDilutionIsSaved() {
        given(dilutionRepository.save(dilution)).willReturn(dilution);
        final Dilution savedDilution = dilutionService.createDilution(dilution);
        assertThat(savedDilution).isNotNull();
        assertThat(dilution).isEqualTo(savedDilution);
        verify(dilutionRepository, only()).save(savedDilution);
    }

    @Test
    public void testThatGetAllDilutions() {
        // given - precondition or setup
        final Dilution dilution1 = TestDataUtil.createDilution();
        given(dilutionRepository.findAll()).willReturn(List.of(dilution, dilution1));
        // when -  action or the behaviour that we are going test
        final List<Dilution> dilutions = dilutionService.getDilutions();
        // then - verify the output
        assertThat(dilutions).isNotNull();
        assertThat(dilutions.size()).isEqualTo(2);
    }

    @Test
    public void testThatGetAllDilutionsThenReturnEmptyDilutionsList() {
        // given - precondition or setup
        final Dilution dilution1 = TestDataUtil.createDilution();
        given(dilutionRepository.findAll()).willReturn(Collections.emptyList());
        // when -  action or the behaviour that we are going test
        final List<Dilution> dilutions = dilutionService.getDilutions();
        // then - verify the output
        assertThat(dilutions).isEmpty();
    }

    @Test
    public void testThatGetDIlutionById() {
        given(dilutionRepository.findById(1L)).willReturn(Optional.of(dilution));
        final Dilution saveddilution = dilutionService.getDilution(dilution.getId()).get();
        assertThat(saveddilution).isNotNull();
    }

    @Test
    public void testThatUpdateDilution() {
        given(dilutionRepository.save(dilution)).willReturn(dilution);
        dilution.setQuantity(30);
        final Dilution updateDilution = dilutionService.updateDilution(dilution);
        assertThat(updateDilution.getQuantity()).isEqualTo(30);
    }


}
