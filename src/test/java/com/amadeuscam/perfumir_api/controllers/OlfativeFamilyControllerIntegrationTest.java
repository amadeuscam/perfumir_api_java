package com.amadeuscam.perfumir_api.controllers;

import com.amadeuscam.perfumir_api.TestDataUtil;
import com.amadeuscam.perfumir_api.entities.Dilution;
import com.amadeuscam.perfumir_api.entities.Ingredient;
import com.amadeuscam.perfumir_api.entities.OlfactiveFamilies;
import com.amadeuscam.perfumir_api.services.IngredientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
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

import java.util.HashSet;
import java.util.Set;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class OlfativeFamilyControllerIntegrationTest {
    private final IngredientService ingredientService;
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @Autowired
    public OlfativeFamilyControllerIntegrationTest(IngredientService ingredientService, MockMvc mockMvc, ObjectMapper objectMapper) {
        this.ingredientService = ingredientService;
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @BeforeEach
    void setUp() {
        OlfactiveFamilies citrus = TestDataUtil.createOlfactiveFamilies(1L, "CITRUS");
        Set<Dilution> dilutions = new HashSet<>();
        Set<OlfactiveFamilies> olfactiveFamilies = new HashSet<>();
        olfactiveFamilies.add(citrus);


        Ingredient ingredient = TestDataUtil.createTestIngredient(1L, dilutions, olfactiveFamilies);
        ingredientService.createIngredient(ingredient);
    }

    @Test
    @WithMockUser(username = "teste@test.es", authorities = {"USER"})
    public void testThatCanReturnTheOlfativeFamilysFromIngredient() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/olfative-families/1")
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.length()").value(1)
                ).andExpect(
                        MockMvcResultMatchers.jsonPath("$[0].name").value("CITRUS")
                );
    }

    @Test
    @WithMockUser(username = "teste@test.es", authorities = {"USER"})
    public void testThatCanReturnTheOlfativeFamilyFromIngredient() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/olfative-families/1/1")
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.name").value("CITRUS")
                );
    }

    @Test
    @WithMockUser(username = "teste@test.es", authorities = {"USER"})
    public void testThatCanAddOlfativeFamilyToIngredient() throws Exception {
        OlfactiveFamilies woody = TestDataUtil.createOlfactiveFamilies(2L, "Woody");
        String json = objectMapper.writeValueAsString(woody);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/olfative-families/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                ).andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.name").value("Woody")
                );
    }

    @Test
    @WithMockUser(username = "teste@test.es", authorities = {"USER"})
    public void testThatCanUpdateOlfativeFamilyFromIngredient() throws Exception {
        OlfactiveFamilies woody = TestDataUtil.createOlfactiveFamilies(1L, "Woody");
        String json = objectMapper.writeValueAsString(woody);
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/v1/olfative-families/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.name").value("Woody")
                );
    }

    @Test
    @WithMockUser(username = "teste@test.es", authorities = {"ADMIN"})
    public void testThatCanDeleteOlfativeFamilyFromIngredient() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/v1/olfative-families/1/1")
                .contentType(MediaType.APPLICATION_JSON)

        ).andExpect(MockMvcResultMatchers.status().isOk());
    }


}
