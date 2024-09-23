package com.goose.pbtest.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@Transactional
@SpringBootTest
public class EmitentControllerLayerTest {

    private final String URL = "http://localhost:8080/";
    private final String correctContent = "{\"card\":\"1111111111111111\"}";

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Sql(value = "classpath:createBank.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void findByCardIsOkTest() throws Exception {

        mockMvc.perform(post(URL + "api/")
                        .content(correctContent)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bin", equalTo("1")));
    }

    @Test
    public void findByCardIsThrownExceptionTest() throws Exception {
        String content1 = "{\"card\":\"\"}";
        String content2 = "{\"card\":\"11111111\"}";
        String content3 = "{\"card\":\"111111111111111a\"}";

        mockMvc.perform(post(URL + "api/")
                        .content(content1)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.length()", equalTo(3)))
                .andExpect(jsonPath("$", hasItem("Should not be emtpy line")));

        mockMvc.perform(post(URL + "api/")
                        .content(content2)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0]", equalTo("Card number should be 16 digits only")));

        mockMvc.perform(post(URL + "api/")
                        .content(content3)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0]", equalTo("Must be only numbers")));

        mockMvc.perform(post(URL + "api/")
                        .content(correctContent)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", equalTo("Information not found")));

    }

    @Test
    public void getHTMLPageIsOkTest() throws Exception{
        mockMvc.perform(get(URL)).andExpect(status().isOk());
    }
}
