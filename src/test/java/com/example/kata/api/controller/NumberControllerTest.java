package com.example.kata.api.controller;

import com.example.kata.api.service.TransformationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ComponentScan(basePackages = "com.example.kata")
class NumberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Configuration
    static class TestConfig {
        @Bean
        public TransformationService transformationService() {
            return mock(TransformationService.class);
        }
    }

    @Autowired
    private TransformationService transformationService;

    @Test
    @DisplayName("GET /api/kata/transform?number=15 should return transformed result")
    void shouldReturnTransformedResultForValidNumber() throws Exception {

        when(transformationService.transform(15)).thenReturn("FOOBARBAR");

        // act & assert
        mockMvc.perform(get("/api/kata/transform")
                        .param("number", "15")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("FOOBARBAR"));
    }

    @Test
    @DisplayName("GET /api/kata/transform?number=-1 should return 400")
    void shouldReturn400ForNegativeNumber() throws Exception {
        mockMvc.perform(get("/api/kata/transform")
                        .param("number", "-1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(org.hamcrest.Matchers.containsString(
                        "Le nombre doit être compris entre 0 et 100")));
    }

    @Test
    @DisplayName("GET /api/kata/transform?number=101 should return 400")
    void shouldReturn400ForNumberAbove100() throws Exception {
        mockMvc.perform(get("/api/kata/transform")
                        .param("number", "101")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(org.hamcrest.Matchers.containsString(
                        "Le nombre doit être compris entre 0 et 100")));
    }

    @Test
    @DisplayName("GET /api/kata/transform without number param should return 400")
    void shouldReturn400WhenNumberParamMissing() throws Exception {
        mockMvc.perform(get("/api/kata/transform")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(org.hamcrest.Matchers.containsString(
                        "Le paramètre 'number' est requis et ne peut être null")));
    }

    @Test
    @DisplayName("GET /api/kata/transform?number=abc should return 400")
    void shouldReturn400ForInvalidNumberFormat() throws Exception {
        mockMvc.perform(get("/api/kata/transform")
                        .param("number", "abc")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(org.hamcrest.Matchers.containsString(
                        "Le paramètre 'number' doit être un entier valide")));
    }
}
