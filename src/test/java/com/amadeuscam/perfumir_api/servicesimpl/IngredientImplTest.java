package com.amadeuscam.perfumir_api.servicesimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.amadeuscam.perfumir_api.entities.Ingredient;
import com.amadeuscam.perfumir_api.repository.IngredientRepository;
import com.amadeuscam.perfumir_api.services.impl.IngredientServiceImpl;

@ExtendWith(MockitoExtension.class)
public class IngredientImplTest {
    @Mock
    private IngredientRepository ingredientRepository;

    @InjectMocks
    private IngredientServiceImpl ingredientService;

    private Ingredient ingredient;

    @BeforeEach
    void setUp() {
        ingredient = new Ingredient();
        ingredient.setId(1L);
        ingredient.setName("Ingredient Name");
        // Set up more fields as needed
    }

    @Test
    void testCreateIngredient() {
        when(ingredientRepository.save(any(Ingredient.class))).thenReturn(ingredient);

        Ingredient createdIngredient = ingredientService.createIngredient(ingredient);

        assertNotNull(createdIngredient);
        assertEquals(ingredient.getId(), createdIngredient.getId());
        verify(ingredientRepository, times(1)).save(ingredient);
    }

    @Test
    void testUpdateIngredient() {
        when(ingredientRepository.save(any(Ingredient.class))).thenReturn(ingredient);

        Ingredient updatedIngredient = ingredientService.updateIngredient(ingredient);

        assertNotNull(updatedIngredient);
        assertEquals(ingredient.getId(), updatedIngredient.getId());
        verify(ingredientRepository, times(1)).save(ingredient);
    }

    @Test
    void testPartialUpdate() {
        when(ingredientRepository.findById(anyLong())).thenReturn(Optional.of(ingredient));
        when(ingredientRepository.save(any(Ingredient.class))).thenReturn(ingredient);

        Ingredient partialUpdatedIngredient = ingredientService.partialUpdate(ingredient);

        assertNotNull(partialUpdatedIngredient);
        assertEquals(ingredient.getId(), partialUpdatedIngredient.getId());
        verify(ingredientRepository, times(1)).findById(ingredient.getId());
        verify(ingredientRepository, times(1)).save(ingredient);
    }

    @Test
    void testPartialUpdateIngredientNotFound() {
        when(ingredientRepository.findById(anyLong())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            ingredientService.partialUpdate(ingredient);
        });

        assertEquals("Ingredient does not exist", exception.getMessage());
        verify(ingredientRepository, times(1)).findById(ingredient.getId());
        verify(ingredientRepository, never()).save(any(Ingredient.class));
    }

    @Test
    void testGetIngredient() {
        when(ingredientRepository.findById(anyLong())).thenReturn(Optional.of(ingredient));

        Optional<Ingredient> foundIngredient = ingredientService.getIngredient(1L);

        assertTrue(foundIngredient.isPresent());
        assertEquals(ingredient.getId(), foundIngredient.get().getId());
        verify(ingredientRepository, times(1)).findById(1L);
    }

    @Test
    void testFindAll() {
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(ingredient);

        when(ingredientRepository.findAll()).thenReturn(ingredients);

        List<Ingredient> foundIngredients = ingredientService.findAll();

        assertNotNull(foundIngredients);
        assertEquals(1, foundIngredients.size());
        verify(ingredientRepository, times(1)).findAll();
    }

    @Test
    void testIsExists() {
        when(ingredientRepository.existsById(anyLong())).thenReturn(true);

        boolean exists = ingredientService.isExists(1L);

        assertTrue(exists);
        verify(ingredientRepository, times(1)).existsById(1L);
    }

    @Test
    void testDelete() {
        doNothing().when(ingredientRepository).deleteById(anyLong());

        ingredientService.delete(1L);

        verify(ingredientRepository, times(1)).deleteById(1L);
    }
}
