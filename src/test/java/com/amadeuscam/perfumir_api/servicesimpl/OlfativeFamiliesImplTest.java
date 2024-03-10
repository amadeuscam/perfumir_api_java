package com.amadeuscam.perfumir_api.servicesimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
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

        OlfactiveFamilies olfactiveFamilies2 = olfativeFamiliesService.createOlfactiveFamilies(olfactiveFamilies,
                ingredientId);
        assertEquals(olfactiveFamilies, olfactiveFamilies2);
        assertNotNull(olfactiveFamilies2.getIngredient());
        assertEquals(ingredient, olfactiveFamilies2.getIngredient());

    }

    @Test
    public void createOlfactiveFamilies_ingredientDoesNotExist_throwsRuntimeException() {
        Long ingredientId = 1L;
        OlfactiveFamilies olfactiveFamilies = new OlfactiveFamilies();

        when(ingredientRepository.findById(ingredientId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> olfativeFamiliesService.createOlfactiveFamilies(olfactiveFamilies, ingredientId));
    }

    @Test
    public void testThatUpdateOlfativeFamilyFromIngredient() {
        Long ingredientId = 1L;
        OlfactiveFamilies olfactiveFamilies = new OlfactiveFamilies();
        Ingredient ingredient = new Ingredient();
        ingredient.setId(ingredientId);
        olfactiveFamilies.setId(2l);

        when(ingredientRepository.findById(1L)).thenReturn(Optional.of(ingredient));
        when(olfactiveFamiliesRepository.save(olfactiveFamilies)).thenReturn(olfactiveFamilies);

        OlfactiveFamilies olfactiveFamilies2 = olfativeFamiliesService.updateOlfactiveFamilies(olfactiveFamilies,
                ingredientId);
        assertEquals(olfactiveFamilies, olfactiveFamilies2);
        assertNotNull(olfactiveFamilies2.getIngredient());
        assertEquals(ingredient, olfactiveFamilies2.getIngredient());

    }

    @Test
    public void testThatUpdateOlfativeFamilyFromIngredientNotId() {
        Long ingredientId = 1L;
        OlfactiveFamilies olfactiveFamilies = new OlfactiveFamilies();
        Ingredient ingredient = new Ingredient();
        ingredient.setId(ingredientId);

        when(ingredientRepository.findById(1L)).thenReturn(Optional.of(ingredient));
        assertThrows(RuntimeException.class,
                () -> olfativeFamiliesService.updateOlfactiveFamilies(olfactiveFamilies, ingredientId));

    }

    @Test
    public void testThatOlfativeFamilyDeleteFromIngredient() {
        Long ingredientId = 1l;
        Long OlfativeId = 2l;

        OlfactiveFamilies olfactiveFamilies = new OlfactiveFamilies();
        Ingredient ingredient = new Ingredient();

        ingredient.setId(ingredientId);
        olfactiveFamilies.setId(OlfativeId);

        Set<OlfactiveFamilies> oplFamilies = new HashSet<>();
        oplFamilies.add(olfactiveFamilies);
        ingredient.setOlfactiveFamilies(oplFamilies);

        when(ingredientRepository.findById(ingredientId)).thenReturn(Optional.of(ingredient));
        when(ingredientRepository.save(ingredient)).thenReturn(ingredient);

        Ingredient deleteOlfactiveFamilies = olfativeFamiliesService.deleteOlfactiveFamilies(OlfativeId, ingredientId);

        assertEquals(ingredient, deleteOlfactiveFamilies);
        assertTrue(ingredient.getOlfactiveFamilies().isEmpty());
    }

    @Test
    public void testThatGetOlfativeFamiliesByIdFromIngredient() {
        Long ingredientId = 1l;
        Long OlfativeId = 2l;

        OlfactiveFamilies olfactiveFamilies = new OlfactiveFamilies();
        Ingredient ingredient = new Ingredient();

        ingredient.setId(ingredientId);
        olfactiveFamilies.setId(OlfativeId);

        Set<OlfactiveFamilies> oplFamilies = new HashSet<>();
        oplFamilies.add(olfactiveFamilies);
        ingredient.setOlfactiveFamilies(oplFamilies);

        when(ingredientRepository.findById(ingredientId)).thenReturn(Optional.of(ingredient));

        Optional<OlfactiveFamilies> olfactiveFamilies2 = olfativeFamiliesService.getOlfactiveFamilies(OlfativeId,
                ingredientId);

        assertEquals(olfactiveFamilies, olfactiveFamilies2.get());
        assertTrue(olfactiveFamilies2.isPresent());
    }

    @Test
    public void testThatGetOlfativeFamiliesFromIngredient() {
        Long ingredientId = 1l;
        Long OlfativeId = 2l;

        OlfactiveFamilies olfactiveFamilies = new OlfactiveFamilies();
        Ingredient ingredient = new Ingredient();

        ingredient.setId(ingredientId);
        olfactiveFamilies.setId(OlfativeId);

        Set<OlfactiveFamilies> oplFamilies = new HashSet<>();
        oplFamilies.add(olfactiveFamilies);
        ingredient.setOlfactiveFamilies(oplFamilies);

        when(ingredientRepository.findById(ingredientId)).thenReturn(Optional.of(ingredient));

        Set<OlfactiveFamilies> olfactiveFamiliess = olfativeFamiliesService.getOlfactiveFamiliess(ingredientId);

        assertEquals(olfactiveFamiliess.size(), 1);
        assertEquals(olfactiveFamilies.getName(), olfactiveFamiliess.stream().findFirst().get().getName());

    }

    @Test
    public void getUniqueOlfactiveFamiliesReturnsUniqueNames() {
        List<String> expectedNames = new ArrayList<>();
        expectedNames.add("Citrus");
        expectedNames.add("Floral");

        when(olfactiveFamiliesRepository.findDistinctByName()).thenReturn(expectedNames);

        List<String> actualNames = olfativeFamiliesService.getUniqueOlfactiveFamilies();

        assertEquals(expectedNames, actualNames);
    }

    @Test
    public void getUniqueOlfactiveFamiliesReturnsEmptyListWhenNoUniqueNamesExist() {
        List<String> expectedNames = new ArrayList<>();

        when(olfactiveFamiliesRepository.findDistinctByName()).thenReturn(expectedNames);

        List<String> actualNames = olfativeFamiliesService.getUniqueOlfactiveFamilies();

        assertEquals(expectedNames, actualNames);
    }

}
