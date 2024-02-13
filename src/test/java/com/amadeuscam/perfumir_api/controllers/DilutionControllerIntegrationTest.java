package com.amadeuscam.perfumir_api.controllers;

import com.amadeuscam.perfumir_api.TestDataUtil;
import com.amadeuscam.perfumir_api.entities.Dilution;
import com.amadeuscam.perfumir_api.entities.Ingredient;
import com.amadeuscam.perfumir_api.entities.OlfactiveFamilies;
import com.amadeuscam.perfumir_api.services.IngredientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.hamcrest.Matchers;
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
import java.util.Optional;
import java.util.Set;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class DilutionControllerIntegrationTest {


    private final IngredientService ingredientService;

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @Autowired
    public DilutionControllerIntegrationTest(IngredientService ingredientService, MockMvc mockMvc) {

        this.ingredientService = ingredientService;
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
    }

    @BeforeEach
    void setUp() {
        final Dilution dilution = TestDataUtil.createDilution(1L);
        Set<Dilution> dilutions = new HashSet<>();
        dilutions.add(dilution);
        Set<OlfactiveFamilies> olfactiveFamilies = new HashSet<>();


        Ingredient ingredient = TestDataUtil.createTestIngredient(1L, dilutions, olfactiveFamilies);
        ingredientService.createIngredient(ingredient);
    }

    @Test
    @WithMockUser(username = "teste@test.es", authorities = {"USER"})
    public void testThatCanReturnTheQuantityOfEachDilution() throws Exception {


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

    @Test
    @WithMockUser(username = "teste@test.es", authorities = {"USER"})
    public void testThatCanGetDilutionFromIngredient() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/dilutions/1/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.quantity").value(10)
        );
    }

    @Test
    @WithMockUser(username = "teste@test.es", authorities = {"USER"})
    @Transactional
    public void testThatCanGetDilutionsFromIngredient() throws Exception {
        final Dilution dilution = TestDataUtil.createDilution(2L);

        Optional<Ingredient> ingredient = ingredientService.getIngredient(1L);
        ingredient.ifPresent(value -> value.getDilutions().add(dilution));


        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/dilutions/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$").isArray()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.length()").value(2)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].quantity").value(10)
        );


    }

    @Test
    @WithMockUser(username = "teste@test.es", authorities = {"USER"})
    public void testThatCanAddDilutionToIngredient() throws Exception {
        final Dilution dilution = TestDataUtil.createDilution(1L);
        final String json = objectMapper.writeValueAsString(dilution);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/v1/dilutions/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)


                ).andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.quantity").value(10)
                );


    }

    @Test
    @WithMockUser(username = "teste@test.es", authorities = {"USER"})
    public void testThatCanUpdateDilutionFromIngredient() throws Exception {
        final Dilution dilution = TestDataUtil.createDilution(1L);
        final String json = objectMapper.writeValueAsString(dilution);

        mockMvc.perform(
                        MockMvcRequestBuilders.put("/api/v1/dilutions/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.quantity").value(10)
                );


    }

    @Test
    @WithMockUser(username = "teste@test.es", authorities = {"ADMIN"})
    public void testThatCanDeleteDilutionFromIngredient() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/v1/dilutions/1/1")
                        .contentType(MediaType.APPLICATION_JSON)

        ).andExpect(MockMvcResultMatchers.status().isOk());


    }


}
