package com.amadeuscam.perfumir_api.servicesimpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.amadeuscam.perfumir_api.repository.IngredientRepository;
import com.amadeuscam.perfumir_api.services.impl.IngredientServiceImpl;
import com.amadeuscam.perfumir_api.services.impl.OlfativeFamiliesServiceImpl;

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
