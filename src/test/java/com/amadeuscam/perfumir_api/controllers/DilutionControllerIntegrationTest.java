package com.amadeuscam.perfumir_api.controllers;

import com.amadeuscam.perfumir_api.TestDataUtil;
import com.amadeuscam.perfumir_api.entities.Dilution;
import com.amadeuscam.perfumir_api.entities.Ingredient;
import com.amadeuscam.perfumir_api.entities.OlfactiveFamilies;
import com.amadeuscam.perfumir_api.services.DilutionService;
import com.amadeuscam.perfumir_api.services.IngredientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashSet;
import java.util.Set;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class DilutionControllerIntegrationTest {


    private final IngredientService ingredientService;

    private final MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Autowired
    public DilutionControllerIntegrationTest(IngredientService ingredientService, MockMvc mockMvc) {

        this.ingredientService = ingredientService;
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    @WithMockUser(username = "teste@test.es", authorities = {"USER"})
    public void testThatCanReturnTheQuantityOfEachDilution() throws Exception {
        final Dilution dilution = TestDataUtil.createDilution(1L);
        Set<Dilution> dilutions = new HashSet<>();
        dilutions.add(dilution);
        Set<OlfactiveFamilies> olfactiveFamilies = new HashSet<>();


        Ingredient ingredient = TestDataUtil.createTestIngredient(1L, dilutions, olfactiveFamilies);
        ingredientService.createIngredient(ingredient);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/v1/dilutions/count-quantity")
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$[0].count").isNumber()
                ).andExpect(
                        MockMvcResultMatchers.jsonPath("$[0].count").value(1)
                ).andExpect(
                        MockMvcResultMatchers.jsonPath("$[0].quantity").value(10)
                );


    }

    @Test
    @WithMockUser(username = "teste@test.es", authorities = {"USER"})
    public void testThatReturnTheDilutionAvailable() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/dilutions/dilution-list")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$").isArray()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$").value(Matchers.containsInAnyOrder(100, 50, 20, 10, 5, 1))
        );
    }

}
