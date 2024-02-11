package com.amadeuscam.perfumir_api.servicesimpl;

import com.amadeuscam.perfumir_api.TestDataUtil;
import com.amadeuscam.perfumir_api.entities.Dilution;
import com.amadeuscam.perfumir_api.entities.Ingredient;
import com.amadeuscam.perfumir_api.repository.DilutionRepository;
import com.amadeuscam.perfumir_api.repository.IngredientRepository;
import com.amadeuscam.perfumir_api.services.DilutionService;
import com.amadeuscam.perfumir_api.services.IngredientService;
import com.amadeuscam.perfumir_api.services.impl.DilutionServiceImpl;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class DilutionImplTest {

//    @Mock
//    private DilutionRepository dilutionRepository;
//    @InjectMocks
//    private DilutionServiceImpl dilutionService;
//
//    @Mock
//    private IngredientRepository ingredientRepository;
//    @Mock
//    private IngredientService ingredientService;
//    private Dilution dilution;
//    @Mock
//    private Ingredient ingredient;
//
//    @BeforeEach
//    void setUp() {
//        dilution = TestDataUtil.createDilution(1L);
//        dilutionRepository.save(dilution);
//    }
//
//    @Test
//    @Transactional
//    public void testThatDilutionIsSaved() {
//        ingredient = TestDataUtil.createTestIngredient(1L, null, null);
//        when(ingredientRepository.save(ingredient)).thenReturn(ingredient);
//        ingredientRepository.saveAndFlush(ingredient);
//        System.out.println(ingredient.getId());
//        Dilution savedDilution = dilutionService.createDilution(dilution, ingredient.getId());
////        ingredientService.createIngredient(testIngredient);
//
//        assertThat(savedDilution).isNotNull();
////        assertThat(savedDilution).isNotNull();
////        assertThat(dilution).isEqualTo(savedDilution);
////        verify(dilutionRepository, only()).save(savedDilution);
//    }



}
