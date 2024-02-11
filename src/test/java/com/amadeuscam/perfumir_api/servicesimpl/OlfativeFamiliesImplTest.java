package com.amadeuscam.perfumir_api.servicesimpl;

import com.amadeuscam.perfumir_api.TestDataUtil;
import com.amadeuscam.perfumir_api.entities.Dilution;
import com.amadeuscam.perfumir_api.entities.Ingredient;
import com.amadeuscam.perfumir_api.entities.OlfactiveFamilies;
import com.amadeuscam.perfumir_api.repository.IngredientRepository;
import com.amadeuscam.perfumir_api.repository.OlfactiveFamiliesRepository;
import com.amadeuscam.perfumir_api.services.IngredientService;
import com.amadeuscam.perfumir_api.services.impl.IngredientServiceImpl;
import com.amadeuscam.perfumir_api.services.impl.OlfativeFamiliesServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OlfativeFamiliesImplTest {

    @Mock
    private OlfativeFamiliesServiceImpl olfativeFamiliesService;

    @Mock
    private IngredientRepository ingredientRepository;
    @InjectMocks
    private IngredientServiceImpl ingredientService;


    @BeforeEach
    void setUp() {

    }






}
