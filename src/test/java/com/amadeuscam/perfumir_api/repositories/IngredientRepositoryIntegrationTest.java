package com.amadeuscam.perfumir_api.repositories;

import com.amadeuscam.perfumir_api.TestDataUtil;
import com.amadeuscam.perfumir_api.entities.Dilution;
import com.amadeuscam.perfumir_api.entities.Ingredient;
import com.amadeuscam.perfumir_api.entities.OlfactiveFamilies;
import com.amadeuscam.perfumir_api.repository.IngredientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)

public class IngredientRepositoryIntegrationTest {

    private IngredientRepository ingredientRepository;

    @Autowired
    public IngredientRepositoryIntegrationTest(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Test
    public void testThatIngredientCanBeCreatedAndRecalled() {
        Set<Dilution> dilutions = new HashSet<>();
        Set<OlfactiveFamilies> olfactiveFamilies = new HashSet<>();

        Ingredient ingredient = TestDataUtil.createTestIngredient(1L, dilutions, olfactiveFamilies);
        ingredientRepository.save(ingredient);
        final Optional<Ingredient> result = ingredientRepository.findById(ingredient.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(ingredient);
    }

    @Test
    public void testThatMultipleIngredientsCanBeCreatedAndRecalled() {
        Set<Dilution> dilutions = new HashSet<>();
        Set<OlfactiveFamilies> olfactiveFamilies = new HashSet<>();

        Ingredient ingredient1 = TestDataUtil.createTestIngredient(1L, dilutions, olfactiveFamilies);
        ingredientRepository.save(ingredient1);
        Ingredient ingredient2 = TestDataUtil.createTestIngredient(2L, dilutions, olfactiveFamilies);
        ingredientRepository.save(ingredient2);
        Ingredient ingredient3 = TestDataUtil.createTestIngredient(3L, dilutions, olfactiveFamilies);
        ingredientRepository.save(ingredient3);

        final Iterable<Ingredient> ingredientList = ingredientRepository.findAll();
        assertThat(ingredientList).hasSize(3).containsExactly(ingredient1, ingredient2, ingredient3);
    }

    @Test
    public void testThatIngredientCanBeUpdated() {
        Set<Dilution> dilutions = new HashSet<>();
        Set<OlfactiveFamilies> olfactiveFamilies = new HashSet<>();

        Ingredient ingredient = TestDataUtil.createTestIngredient(1L, dilutions, olfactiveFamilies);
        ingredientRepository.save(ingredient);
        ingredient.setName("Ambrxaan");
        ingredientRepository.save(ingredient);

        final Optional<Ingredient> result = ingredientRepository.findById(ingredient.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(ingredient);
    }

}
