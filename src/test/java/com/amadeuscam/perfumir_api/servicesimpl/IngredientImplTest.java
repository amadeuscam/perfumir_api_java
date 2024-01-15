package com.amadeuscam.perfumir_api.servicesimpl;

import com.amadeuscam.perfumir_api.TestDataUtil;
import com.amadeuscam.perfumir_api.entities.Dilution;
import com.amadeuscam.perfumir_api.entities.Ingredient;
import com.amadeuscam.perfumir_api.entities.OlfactiveFamilies;
import com.amadeuscam.perfumir_api.repository.IngredientRepository;
import com.amadeuscam.perfumir_api.services.impl.IngredientServiceImpl;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RequiredArgsConstructor
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class IngredientImplTest {
    @Mock
    private IngredientRepository ingredientRepository;
    @InjectMocks
    private IngredientServiceImpl underTest;

    @Test
    public void testThatIngredientIsSaved() {
        final Ingredient ingredient = TestDataUtil.createTestIngredient(1L, null, null);
        when(ingredientRepository.save(eq(ingredient))).thenReturn(ingredient);
        final Ingredient result = underTest.createIngredient(ingredient);
        assertEquals(ingredient, result);
    }

    @Test
    public void testThatIngredientIsSavedWithDilution() {
        final Dilution dilution = TestDataUtil.createDilution();
        final Ingredient ingredient = TestDataUtil.createTestIngredient(1L, null, null);
        ingredient.setDilutions(Set.of(dilution));
        when(ingredientRepository.save(eq(ingredient))).thenReturn(ingredient);
        final Ingredient result = underTest.createIngredient(ingredient);
        assertEquals(ingredient, result);
        assertEquals(ingredient.getDilutions().size(), 1);

    }

    @Test
    public void testThatIngredientIsSavedWithDilutionAndOlfactiveFamilies() {
        final Dilution dilution = TestDataUtil.createDilution();
        final OlfactiveFamilies olfactiveFamilies = TestDataUtil.createOlfactiveFamilies("Marine");

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
