package com.example.pix.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.pix.dto.PixRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class PixControllerIT {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void createAndGetPixFlow() throws Exception {
        PixRequest req = new PixRequest("payer-1", "receiver-1", new BigDecimal("10.00"));
        String payload = mapper.writeValueAsString(req);
        String location = mvc.perform(post("/api/pix")
                        .contentType(MediaType.APPLICATION_JSON).content(payload))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getHeader("Location");
        String id = location.substring(location.lastIndexOf('/') + 1);
        mvc.perform(get("/api/pix/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.payer").value("payer-1"))
                .andExpect(jsonPath("$.amount").value(10.00));
    }
    
}
