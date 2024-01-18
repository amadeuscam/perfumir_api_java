package com.amadeuscam.perfumir_api.controllers;

import com.amadeuscam.perfumir_api.TestDataUtil;
import com.amadeuscam.perfumir_api.entities.Dilution;
import com.amadeuscam.perfumir_api.entities.Ingredient;
import com.amadeuscam.perfumir_api.entities.OlfactiveFamilies;
import com.amadeuscam.perfumir_api.services.IngredientService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class IngredientControllerIntegrationTest {

    private IngredientService ingredientService;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    public IngredientControllerIntegrationTest(IngredientService ingredientService, MockMvc mockMvc) {
        this.ingredientService = ingredientService;
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
    }


    @Test
    @WithMockUser(username = "teste@test.es", authorities = {"USER"})
    public void testThatCanCreateIngredientWith201Created() throws Exception {
        Set<Dilution> dilutions = new HashSet<>();
        Set<OlfactiveFamilies> olfactiveFamilies = new HashSet<>();


        Ingredient ingredient = TestDataUtil.createTestIngredient(1L, dilutions, olfactiveFamilies);
        final String json = objectMapper.writeValueAsString(ingredient);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/ingredients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(MockMvcResultMatchers.status().isCreated());


    }

    @Test
    @WithMockUser(username = "test@test.es", authorities = {"USER"})
    public void testThatCreateIngredientWithSavedIngredient() throws Exception {
        Set<Dilution> dilutions = new HashSet<>();
        Set<OlfactiveFamilies> olfactiveFamilies = new HashSet<>();


        Ingredient ingredient = TestDataUtil.createTestIngredient(1L, dilutions, olfactiveFamilies);
        final String json = objectMapper.writeValueAsString(ingredient);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/ingredients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Ambroxan")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.casNumber").value("3738-00-9")
        );

    }

    @Test
    @WithMockUser(username = "test@test.es", authorities = {"USER"})
    public void testThatListOfIngredientsReturnsStaus200() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/ingredients")
                        .contentType(MediaType.APPLICATION_JSON)

        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(username = "test@test.es", authorities = {"USER"})
    public void testThatReturnsListOfIngredient() throws Exception {
        Ingredient ingredient = TestDataUtil.createTestIngredient(1L, null, null);
        ingredientService.createIngredient(ingredient);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/ingredients")
                        .contentType(MediaType.APPLICATION_JSON)

        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].name").value("Ambroxan")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].casNumber").value("3738-00-9")
        );
    }

    @Test
    @WithMockUser(username = "test@test.es", authorities = {"USER"})
    public void testThatReturn200StatusCodeWhenIngredientFound() throws Exception {
        Ingredient ingredient = TestDataUtil.createTestIngredient(1L, null, null);
        ingredientService.createIngredient(ingredient);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/ingredients/1")
                        .contentType(MediaType.APPLICATION_JSON)

        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(username = "test@test.es", authorities = {"USER"})
    public void testThatReturn404StatusCodeWhenIngredientNotFound() throws Exception {
        Ingredient ingredient = TestDataUtil.createTestIngredient(1L, null, null);
        ingredientService.createIngredient(ingredient);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/ingredients/12")
                        .contentType(MediaType.APPLICATION_JSON)

        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @WithMockUser(username = "test@test.es", authorities = {"USER"})
    public void testThatGetAuthorWhenExists() throws Exception {
        Ingredient ingredient = TestDataUtil.createTestIngredient(1L, null, null);
        ingredientService.createIngredient(ingredient);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/ingredients/1")
                        .contentType(MediaType.APPLICATION_JSON)

        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Ambroxan")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.casNumber").value("3738-00-9")
        );
    }

    @Test
    @WithMockUser(username = "test@test.es", authorities = {"USER"})
    public void testThatPartialUpdateIngredientAndReturnedUpdated() throws Exception {

        Ingredient ingredient = TestDataUtil.createTestIngredient(1L, null, null);
        ingredientService.createIngredient(ingredient);

        Ingredient testIngredient = TestDataUtil.createTestIngredient(1L, null, null);
        testIngredient.setName("Updated");
        final String json = objectMapper.writeValueAsString(testIngredient);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/api/v1/ingredients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)

        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(ingredient.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Updated")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.casNumber").value(testIngredient.getCasNumber())
        );
    }

    @Test
    @WithMockUser(username = "test@test.es", authorities = {"ADMIN"})
    public void testThatDeleteIngredientAndReturn204Status() throws Exception {
        Ingredient ingredient = TestDataUtil.createTestIngredient(1L, null, null);
        ingredientService.createIngredient(ingredient);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/v1/ingredients/1")
                        .contentType(MediaType.APPLICATION_JSON)

        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @WithMockUser(username = "test@test.es", authorities = {"USER"})
    public void testThatDeleteIngredientAndReturn403Status() throws Exception {
        Ingredient ingredient = TestDataUtil.createTestIngredient(1L, null, null);
        ingredientService.createIngredient(ingredient);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/v1/ingredients/1")
                        .contentType(MediaType.APPLICATION_JSON)

        ).andExpect(MockMvcResultMatchers.status().is(403));
    }
}
