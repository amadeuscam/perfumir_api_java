package com.amadeuscam.perfumir_api.servicesimpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.amadeuscam.perfumir_api.TestDataUtil;
import com.amadeuscam.perfumir_api.dto.DilutionCountDto;
import com.amadeuscam.perfumir_api.entities.Dilution;
import com.amadeuscam.perfumir_api.entities.Ingredient;
import com.amadeuscam.perfumir_api.repository.DilutionRepository;
import com.amadeuscam.perfumir_api.repository.IngredientRepository;
import com.amadeuscam.perfumir_api.services.impl.DilutionServiceImpl;


@ExtendWith(MockitoExtension.class)
public class DilutionImplTest {
    @Mock
    private IngredientRepository ingredientRepository;
    @Mock
    private DilutionRepository dilutionRepository;

    @InjectMocks
    private DilutionServiceImpl dilutionService;


    Ingredient testIngredient;
    Dilution dilution;

    @BeforeEach
    public void init() {
        testIngredient = TestDataUtil.createTestIngredient(1L, null, null);
        dilution = TestDataUtil.createDilution(1L);
    }

    @Test
    public void testThatCreateDilutionForIngredient() {
        when(ingredientRepository.findById(testIngredient.getId())).thenReturn(Optional.of(testIngredient));
        when(dilutionRepository.save(dilution)).thenReturn(dilution);

        Dilution serviceDilution = dilutionService.createDilution(dilution, 1L);
        assertThat(serviceDilution).isNotNull();
    }

    @Test
    public void testThatUpdateDilutionForIngredient() {
        when(ingredientRepository.findById(testIngredient.getId())).thenReturn(Optional.of(testIngredient));
        when(dilutionRepository.save(dilution)).thenReturn(dilution);

        Dilution serviceDilution = dilutionService.updateDilution(dilution, 1L);
        assertThat(serviceDilution).isNotNull();
    }

    @Test
    public void testThatDeleteDilutionForIngredient() {
        Set<Dilution> dilutions = new HashSet<>();
        dilutions.add(dilution);
        testIngredient.setDilutions(dilutions);
        when(ingredientRepository.save(testIngredient)).thenReturn(testIngredient);
        when(ingredientRepository.findById(testIngredient.getId())).thenReturn(Optional.of(testIngredient));
        Ingredient ingredient = dilutionService.deleteDilution(1L, 1L);
        assertThat(ingredient.getDilutions().size()).isEqualTo(0);
    }

    @Test
    public void testThatGetDilutionForIngredient() {
        Set<Dilution> dilutions = new HashSet<>();
        dilutions.add(dilution);
        testIngredient.setDilutions(dilutions);
        when(ingredientRepository.findById(testIngredient.getId())).thenReturn(Optional.of(testIngredient));
        Optional<Dilution> optionalDilution = dilutionService.getDilution(1L, 1L);
        assertThat(optionalDilution).isPresent();
        assertThat(optionalDilution.get().getQuantity()).isEqualTo(10);
    }

    @Test
    public void testThatGetDilutionsForIngredient() {
        Set<Dilution> dilutions = new HashSet<>();
        Dilution built = Dilution.builder()
                .id(2L)
                .quantity(20)
                .build();
        dilutions.add(dilution);
        dilutions.add(built);
        testIngredient.setDilutions(dilutions);
        when(ingredientRepository.findById(testIngredient.getId())).thenReturn(Optional.of(testIngredient));
        Set<Dilution> dilutionSet = dilutionService.getDilutions(1L);
        assertThat(dilutionSet.size()).isEqualTo(2);

    }

    @Test
    public void testThatGetDilutionsAndQuantities() {
        List<DilutionCountDto> dilutionsQuantities = dilutionService.getDilutionsQuantities();
        assertThat(dilutionsQuantities).isNotNull();
    }

}
