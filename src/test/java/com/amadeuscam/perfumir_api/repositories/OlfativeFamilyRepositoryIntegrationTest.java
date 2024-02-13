package com.amadeuscam.perfumir_api.repositories;

import com.amadeuscam.perfumir_api.TestDataUtil;
import com.amadeuscam.perfumir_api.entities.OlfactiveFamilies;
import com.amadeuscam.perfumir_api.repository.IngredientRepository;
import com.amadeuscam.perfumir_api.repository.OlfactiveFamiliesRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@DataJpaTest
public class OlfativeFamilyRepositoryIntegrationTest {

    @Autowired
    private OlfactiveFamiliesRepository olfactiveFamiliesRepository;


    @Test
    public void testThatOlfativeFamilyCanBeAddedAndFounded() {
        OlfactiveFamilies citrus = TestDataUtil.createOlfactiveFamilies(1L, "CITRUS");

        OlfactiveFamilies saved = olfactiveFamiliesRepository.save(citrus);
        Optional<OlfactiveFamilies> optional = olfactiveFamiliesRepository.findById(saved.getId());

        assertThat(optional).isPresent();
        assertThat(optional.get().getName()).isEqualTo("CITRUS");


    }

    @Test
    public void testThatOlfativeFamilyCanBeUpdated() {
        OlfactiveFamilies citrus = TestDataUtil.createOlfactiveFamilies(1L, "CITRUS");

        olfactiveFamiliesRepository.save(citrus);
        OlfactiveFamilies woody = TestDataUtil.createOlfactiveFamilies(1L, "Woody");
        olfactiveFamiliesRepository.save(woody);

        Optional<OlfactiveFamilies> optional = olfactiveFamiliesRepository.findById(1L);

        assertThat(optional).isPresent();
        assertThat(optional.get().getName()).isEqualTo("Woody");


    }

    @Test
    public void testThatOlfativeFamilyCanBeDeleted() {
        OlfactiveFamilies citrus = TestDataUtil.createOlfactiveFamilies(1L, "CITRUS");
        OlfactiveFamilies saved = olfactiveFamiliesRepository.save(citrus);
        olfactiveFamiliesRepository.deleteById(saved.getId());
        assertThat(olfactiveFamiliesRepository.findById(1L)).isEmpty();
    }
}
