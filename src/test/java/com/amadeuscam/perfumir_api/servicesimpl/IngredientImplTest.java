package com.amadeuscam.perfumir_api.servicesimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.annotation.DirtiesContext;

import com.amadeuscam.perfumir_api.TestDataUtil;
import com.amadeuscam.perfumir_api.entities.Dilution;
import com.amadeuscam.perfumir_api.entities.Ingredient;
import com.amadeuscam.perfumir_api.entities.OlfactiveFamilies;
import com.amadeuscam.perfumir_api.repository.IngredientRepository;
import com.amadeuscam.perfumir_api.services.impl.IngredientServiceImpl;
import com.amadeuscam.perfumir_api.services.impl.OlfativeFamiliesServiceImpl;

@ExtendWith(MockitoExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class IngredientImplTest {
    @Mock
    private IngredientRepository ingredientRepository;

    @InjectMocks
    private IngredientServiceImpl underTest;

    @Mock
    private OlfativeFamiliesServiceImpl olfativeFamiliesService;

    @Test
    public void testThatIngredientIsSaved() {
        final Ingredient ingredient = TestDataUtil.createTestIngredient(1L, null, null);
        when(ingredientRepository.save(eq(ingredient))).thenReturn(ingredient);
        final Ingredient result = underTest.createIngredient(ingredient);
        assertEquals(ingredient, result);
    }

    @Test
    public void testThatIngredientIsSavedWithDilution() {
        final Dilution dilution = TestDataUtil.createDilution(1L);
        final Ingredient ingredient = TestDataUtil.createTestIngredient(1L, null, null);
        ingredient.setDilutions(Set.of(dilution));
        when(ingredientRepository.save(eq(ingredient))).thenReturn(ingredient);
        final Ingredient result = underTest.createIngredient(ingredient);
        assertEquals(ingredient, result);
        assertEquals(ingredient.getDilutions().size(), 1);

    }

    @Test
    public void testThatIngredientIsSavedWithDilutionAndOlfactiveFamilies() {
        final Dilution dilution = TestDataUtil.createDilution(1L);
        final OlfactiveFamilies olfactiveFamilies = TestDataUtil.createOlfactiveFamilies(1L, "Marine");

        final Ingredient ingredient = TestDataUtil.createTestIngredient(1L, null, null);
        ingredient.setDilutions(Set.of(dilution));
        ingredient.setOlfactiveFamilies(Set.of(olfactiveFamilies));
        when(ingredientRepository.save(eq(ingredient))).thenReturn(ingredient);
        final Ingredient result = underTest.createIngredient(ingredient);
        assertEquals(ingredient, result);
        assertEquals(ingredient.getDilutions().size(), 1);
        assertEquals(ingredient.getOlfactiveFamilies().size(), 1);

    }
}
