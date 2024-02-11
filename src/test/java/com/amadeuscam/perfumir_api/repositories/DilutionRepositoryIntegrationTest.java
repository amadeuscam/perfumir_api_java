package com.amadeuscam.perfumir_api.repositories;

import com.amadeuscam.perfumir_api.TestDataUtil;
import com.amadeuscam.perfumir_api.entities.Dilution;
import com.amadeuscam.perfumir_api.entities.Ingredient;
import com.amadeuscam.perfumir_api.entities.OlfactiveFamilies;
import com.amadeuscam.perfumir_api.repository.DilutionRepository;
import com.amadeuscam.perfumir_api.repository.IngredientRepository;
import com.amadeuscam.perfumir_api.services.IngredientService;
import com.amadeuscam.perfumir_api.services.impl.IngredientServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@DataJpaTest
public class DilutionRepositoryIntegrationTest {

    @Autowired
    private   DilutionRepository underTest;
    @Autowired
    private   IngredientRepository ingredientRepository;



    @Test
    public void testThatDilutionCanBeCreated() {
        Set<Dilution> dilutions = new HashSet<>();
        Set<OlfactiveFamilies> olfactiveFamilies = new HashSet<>();

        final Dilution dilution = TestDataUtil.createDilution(1L);
        Ingredient ingredient = TestDataUtil.createTestIngredient(2L, dilutions, olfactiveFamilies);

        ingredientRepository.save(ingredient);
        dilution.setIngredient(ingredient);

        final Dilution saved = underTest.save(dilution);
        final Optional<Dilution> optionalDilution = underTest.findById(saved.getId());

        assertThat(optionalDilution).isPresent();
        assertThat(optionalDilution.get().getQuantity()).isEqualTo(10);
        assertThat(optionalDilution.get().getIngredient()).isNotNull();
    }

    @Test
    public void testThatDilutionIsCreatedWhenSaveIngredient() {
        final Dilution dilution = TestDataUtil.createDilution(1L);
        Set<Dilution> dilutions = new HashSet<>();
        dilutions.add(dilution);
        Ingredient ingredient = TestDataUtil.createTestIngredient(1L, null, null);
        ingredient.setDilutions(dilutions);
        final Ingredient serviceIngredient = ingredientRepository.save(ingredient);
        assertThat(serviceIngredient.getDilutions().size()).isEqualTo(1);
        assertThat(serviceIngredient.getDilutions().stream().findFirst().get().getQuantity()).isEqualTo(dilution.getQuantity());

    }

    @Test
    public void testThatDilutionIsDeletedWhenUpdateIngredient() {
        final Dilution dilution = TestDataUtil.createDilution(1L);
        Set<Dilution> dilutions = new HashSet<>();
        Set<OlfactiveFamilies> olfactiveFamilies = new HashSet<>();
        dilutions.add(dilution);
        Ingredient ingredient = TestDataUtil.createTestIngredient(1L, dilutions, olfactiveFamilies);
        final Ingredient serviceIngredient1 = ingredientRepository.save(ingredient);
        serviceIngredient1.getDilutions().removeIf(dilution1 -> Objects.equals(dilution1.getId(), dilution.getId()));

        final Ingredient serviceIngredient = ingredientRepository.save(serviceIngredient1);
        assertThat(serviceIngredient.getDilutions().size()).isEqualTo(0);
    }

    @Test
    public void testThatDilutionCanBeUpdated() {
        final Dilution dilution = TestDataUtil.createDilution(1L);
        underTest.save(dilution);
        dilution.setQuantity(60);
        underTest.save(dilution);
        final Optional<Dilution> dilutionOptional = underTest.findById(dilution.getId());
        assertThat(dilutionOptional).isPresent();
        assertThat(dilutionOptional.get().getQuantity()).isEqualTo(60);

    }


}
