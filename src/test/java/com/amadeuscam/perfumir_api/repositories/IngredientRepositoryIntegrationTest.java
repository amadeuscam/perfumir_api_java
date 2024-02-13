package com.amadeuscam.perfumir_api.repositories;

import com.amadeuscam.perfumir_api.TestDataUtil;
import com.amadeuscam.perfumir_api.entities.Dilution;
import com.amadeuscam.perfumir_api.entities.Ingredient;
import com.amadeuscam.perfumir_api.entities.OlfactiveFamilies;
import com.amadeuscam.perfumir_api.repository.IngredientRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@DataJpaTest
public class IngredientRepositoryIntegrationTest {

    @Autowired
    private IngredientRepository ingredientRepository;


    @Test
    public void testThatIngredientCanBeCreatedAndRecalled() {
        Set<Dilution> dilutions = new HashSet<>();
        Set<OlfactiveFamilies> olfactiveFamilies = new HashSet<>();

        Ingredient ingredient = TestDataUtil.createTestIngredient(10L, dilutions, olfactiveFamilies);
        ingredientRepository.save(ingredient);
        final Optional<Ingredient> result = ingredientRepository.findById(ingredient.getId());
        assertThat(result).isPresent();
        assertThat(result.get().getCasNumber()).isEqualTo("3738-00-9");
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
        assertThat(result.get().getName()).isEqualTo("Ambrxaan");
    }

    @Test
    public void testThatIngredientCanBeDeleted() {
        Set<Dilution> dilutions = new HashSet<>();
        Set<OlfactiveFamilies> olfactiveFamilies = new HashSet<>();

        Ingredient ingredient = TestDataUtil.createTestIngredient(1L, dilutions, olfactiveFamilies);
        ingredientRepository.save(ingredient);

        ingredientRepository.deleteById(1L);
        assertThat(ingredientRepository.findById(1L)).isEmpty();
    }


}
