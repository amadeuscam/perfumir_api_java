package com.amadeuscam.perfumir_api.controllers;

import com.amadeuscam.perfumir_api.TestDataUtil;
import com.amadeuscam.perfumir_api.entities.Dilution;
import com.amadeuscam.perfumir_api.services.DilutionService;
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

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class DilutionControllerIntegrationTest {

    private final DilutionService dilutionService;

    private final MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    public DilutionControllerIntegrationTest(DilutionService dilutionService, MockMvc mockMvc) {
        this.dilutionService = dilutionService;
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    @WithMockUser(username = "teste@test.es", authorities = {"USER"})
    public void testThatCanCreateIngredientWith201Created() throws Exception {


        final Dilution dilution = TestDataUtil.createDilution(1L);
        final String json = objectMapper.writeValueAsString(dilution);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/dilutions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(MockMvcResultMatchers.status().isCreated());


    }

    @Test
    @WithMockUser(username = "teste@test.es", authorities = {"USER"})
    public void testThatCreateDilutionAndCheckIsValues() throws Exception {
        final Dilution dilution = TestDataUtil.createDilution(1L);
        final String json = objectMapper.writeValueAsString(dilution);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/dilutions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(1L)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.quantity").value(10)
        );
    }

    @Test
    @WithMockUser(username = "test@test.es", authorities = {"USER"})
    public void testThatListOfDilutionsReturnsStatus200() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/dilutions")
                        .contentType(MediaType.APPLICATION_JSON)

        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(username = "test@test.es", authorities = {"USER"})
    public void testThatReturn404StatusCodeWhenDilutionNotFound() throws Exception {
        final Dilution dilution = TestDataUtil.createDilution(1L);
        dilutionService.createDilution(dilution);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/dilutions/12")
                        .contentType(MediaType.APPLICATION_JSON)

        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @WithMockUser(username = "test@test.es", authorities = {"USER"})
    public void testThatReturn200StatusCodeWhenDilutionFound() throws Exception {
        final Dilution dilution = TestDataUtil.createDilution(1L);
        dilutionService.createDilution(dilution);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/dilutions/1")
                        .contentType(MediaType.APPLICATION_JSON)

        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(username = "test@test.es", authorities = {"USER"})
    public void testThatReturn200StatusCodeWhenDilutionUpdated() throws Exception {
        final Dilution dilution = TestDataUtil.createDilution(1L);
        dilutionService.createDilution(dilution);
        dilution.setQuantity(30);
        final String json = objectMapper.writeValueAsString(dilution);
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/api/v1/dilutions")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.id").isNumber()
                ).andExpect(
                        MockMvcResultMatchers.jsonPath("$.id").value(1L)
                ).andExpect(
                        MockMvcResultMatchers.jsonPath("$.quantity").value(30)
                );
    }
}
