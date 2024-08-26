package com.amadeuscam.perfumir_api.servicesimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.amadeuscam.perfumir_api.entities.Ingredient;
import com.amadeuscam.perfumir_api.entities.OlfactiveFamilies;
import com.amadeuscam.perfumir_api.repository.IngredientRepository;
import com.amadeuscam.perfumir_api.repository.OlfactiveFamiliesRepository;
import com.amadeuscam.perfumir_api.services.impl.OlfativeFamiliesServiceImpl;

@ExtendWith(MockitoExtension.class)
public class OlfativeFamiliesImplTest {

    @Mock
    private IngredientRepository ingredientRepository;

    @Mock
    private OlfactiveFamiliesRepository olfactiveFamiliesRepository;

    @InjectMocks
    private OlfativeFamiliesServiceImpl olfativeFamiliesService;

    @Test
    public void testThatAddOlfativeFamilyToIngredient() {
        Long ingredientId = 1L;
        OlfactiveFamilies olfactiveFamilies = new OlfactiveFamilies();
        Ingredient ingredient = new Ingredient();
        ingredient.setId(ingredientId);

        when(ingredientRepository.findById(1L)).thenReturn(Optional.of(ingredient));
        when(olfactiveFamiliesRepository.save(olfactiveFamilies)).thenReturn(olfactiveFamilies);

        OlfactiveFamilies result = olfativeFamiliesService.createOlfactiveFamilies(olfactiveFamilies, ingredientId);
        assertNotNull(result);
        assertEquals(ingredient, result.getIngredient());
        verify(olfactiveFamiliesRepository).save(olfactiveFamilies);

    }

    @Test
    public void createOlfactiveFamilies_ingredientDoesNotExist_throwsRuntimeException() {
        Long ingredientId = 1L;
        OlfactiveFamilies olfactiveFamilies = new OlfactiveFamilies();

        when(ingredientRepository.findById(ingredientId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> olfativeFamiliesService.createOlfactiveFamilies(olfactiveFamilies, ingredientId));

        verify(ingredientRepository).findById(ingredientId);
        verify(olfactiveFamiliesRepository, never()).save(any(OlfactiveFamilies.class));
    }

    @Test
    public void testUpdateOlfactiveFamilies_Success() {
        Long ingredientId = 1L;

        Ingredient ingredient = new Ingredient();
        ingredient.setId(ingredientId);

        OlfactiveFamilies olfactiveFamilies = new OlfactiveFamilies();
        olfactiveFamilies.setId(1L);

        when(ingredientRepository.findById(ingredientId)).thenReturn(Optional.of(ingredient));
        when(olfactiveFamiliesRepository.save(olfactiveFamilies)).thenReturn(olfactiveFamilies);

        OlfactiveFamilies result = olfativeFamiliesService.updateOlfactiveFamilies(olfactiveFamilies,
                ingredientId);
        assertEquals(olfactiveFamilies, result);
        assertNotNull(result.getIngredient());
        assertEquals(ingredient, result.getIngredient());

    }

    @Test
    public void testUpdateOlfactiveFamilies_NoId() {
        Long ingredientId = 1L;

        Ingredient ingredient = new Ingredient();
        ingredient.setId(ingredientId);

        OlfactiveFamilies olfactiveFamilies = new OlfactiveFamilies();

        when(ingredientRepository.findById(ingredientId)).thenReturn(Optional.of(ingredient));

        assertThrows(RuntimeException.class,
                () -> olfativeFamiliesService.updateOlfactiveFamilies(olfactiveFamilies, ingredientId));

        verify(ingredientRepository).findById(ingredientId);
        verify(olfactiveFamiliesRepository, never()).save(any(OlfactiveFamilies.class));
    }

    @Test
    public void testDeleteOlfactiveFamilies_Success() {

        Long ingredientId = 1l;
        Long OlfativeId = 2l;

        Ingredient ingredient = new Ingredient();
        ingredient.setId(ingredientId);

        OlfactiveFamilies olfactiveFamily = new OlfactiveFamilies();
        olfactiveFamily.setId(OlfativeId);

        Set<OlfactiveFamilies> olfactiveFamiliesSet = new HashSet<>();
        olfactiveFamiliesSet.add(olfactiveFamily);
        ingredient.setOlfactiveFamilies(olfactiveFamiliesSet);

        when(ingredientRepository.findById(ingredientId)).thenReturn(Optional.of(ingredient));
        when(ingredientRepository.save(ingredient)).thenReturn(ingredient);

        Ingredient result = olfativeFamiliesService.deleteOlfactiveFamilies(OlfativeId, ingredientId);
        assertNotNull(result);
        assertFalse(result.getOlfactiveFamilies().contains(olfactiveFamily));
        verify(ingredientRepository).findById(ingredientId);
        verify(ingredientRepository).save(ingredient);
    }

    @Test
    public void testDeleteOlfactiveFamilies_NotFound() {
        // Datos de prueba
        Long ingredientId = 1L;
        Long olfactiveFamilyId = 2L;

        Ingredient ingredient = new Ingredient();
        ingredient.setId(ingredientId);

        when(ingredientRepository.findById(ingredientId)).thenReturn(Optional.of(ingredient));

        // Ejecución y verificación
        assertThrows(RuntimeException.class, () -> {
            olfativeFamiliesService.deleteOlfactiveFamilies(olfactiveFamilyId, ingredientId);
        });

        verify(ingredientRepository).findById(ingredientId);
        verify(ingredientRepository, never()).save(any(Ingredient.class));
    }

    @Test
    public void testGetOlfactiveFamilies_Success() {
        // Datos de prueba
        Long ingredientId = 1L;
        Long olfactiveFamilyId = 2L;

        Ingredient ingredient = new Ingredient();
        ingredient.setId(ingredientId);

        OlfactiveFamilies olfactiveFamily = new OlfactiveFamilies();
        olfactiveFamily.setId(olfactiveFamilyId);

        Set<OlfactiveFamilies> olfactiveFamiliesSet = new HashSet<>();
        olfactiveFamiliesSet.add(olfactiveFamily);
        ingredient.setOlfactiveFamilies(olfactiveFamiliesSet);

        when(ingredientRepository.findById(ingredientId)).thenReturn(Optional.of(ingredient));

        // Ejecución
        Optional<OlfactiveFamilies> result = olfativeFamiliesService.getOlfactiveFamilies(olfactiveFamilyId,
                ingredientId);

        // Verificación
        assertTrue(result.isPresent());
        assertEquals(olfactiveFamily, result.get());
        verify(ingredientRepository).findById(ingredientId);
    }

    @Test
    public void testGetOlfactiveFamilies_IngredientNotFound() {
        // Datos de prueba
        Long ingredientId = 1L;
        Long olfactiveFamilyId = 2L;

        when(ingredientRepository.findById(ingredientId)).thenReturn(Optional.empty());

        // Ejecución y verificación
        assertThrows(RuntimeException.class, () -> {
            olfativeFamiliesService.getOlfactiveFamilies(olfactiveFamilyId, ingredientId);
        });

        verify(ingredientRepository).findById(ingredientId);
    }

    @Test
    public void testGetUniqueOlfactiveFamilies_Success() {
        // Datos de prueba
        List<String> uniqueFamilies = List.of("Floral", "Woody", "Citrus");

        when(olfactiveFamiliesRepository.findDistinctByName()).thenReturn(uniqueFamilies);

        // Ejecución
        List<String> result = olfativeFamiliesService.getUniqueOlfactiveFamilies();

        // Verificación
        assertNotNull(result);
        assertEquals(uniqueFamilies.size(), result.size());
        assertEquals(uniqueFamilies, result);
        verify(olfactiveFamiliesRepository).findDistinctByName();
    }
}
