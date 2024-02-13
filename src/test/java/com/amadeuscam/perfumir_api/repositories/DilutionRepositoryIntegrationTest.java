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
    private DilutionRepository underTest;


    @Test
    public void testThatDilutionCanBeCreatedAndFindIt() {

        final Dilution dilution = TestDataUtil.createDilution(1L);
        final Dilution saved = underTest.save(dilution);

        final Optional<Dilution> optionalDilution = underTest.findById(saved.getId());

        assertThat(optionalDilution).isPresent();
        assertThat(optionalDilution.get().getQuantity()).isEqualTo(10);
    }

    @Test
    public void testThatDilutionIsCreatedAndUpdated() {
        final Dilution dilution = TestDataUtil.createDilution(1L);
        underTest.save(dilution);
        Dilution dilution1 = Dilution.builder()
                .id(1L)
                .quantity(20)
                .build();
        underTest.save(dilution1);
        final Optional<Dilution> optionalDilution = underTest.findById(1L);

        assertThat(optionalDilution).isPresent();
        assertThat(optionalDilution.get().getQuantity()).isEqualTo(20);
    }


    @Test
    public void testThatDilutionCanBeDeleted() {
        final Dilution dilution = TestDataUtil.createDilution(1L);
        Dilution saved = underTest.save(dilution);
        underTest.deleteById(saved.getId());
        assertThat(underTest.findById(1L)).isEmpty();
    }


}
