package com.demo.course.mvc.testing.financial.controller;

import com.demo.course.mvc.testing.financial.model.DataTransferDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BankOperationsControllerWebClientTest {

    /*Important: The integration tests do not read mocked data but those of the DB,
     *so they can fail with the DB modifications.
     *
     * One possible solution is to use @Order (number)*/

    @Autowired
    private WebTestClient client;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp(){
        objectMapper = new ObjectMapper();
    }

    @Test
    void testDetails() {
        client.get().uri("/api/financial/account/4").exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()

                //Then
                .jsonPath("$.person").value(v -> assertEquals("Thomas Smith", v))
                .jsonPath("$.balance").value(v -> assertEquals(new Double("8978.45"), v));
    }

    @Test
    void testGetBalanceAccount() {
        client.get().uri("/api/financial/account/balance/3").exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()

                //Then
                .jsonPath("$.balance").value(v -> assertEquals(new Double("645345.23"), v));
    }

    @Test
    void testGetTransferBank() {
        client.get().uri("/api/financial/bank/transfer/2").exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()

                //Then
                .jsonPath("$.balance").value(v -> assertEquals(3000, v));
    }

    @Test
    void testTransfer() throws JsonProcessingException {
        DataTransferDTO transfer = new DataTransferDTO(1L, 2L, 1L, new BigDecimal("500"));

        Map<String, Object> response = new HashMap<>();
        response.put("date", LocalDate.now().toString());
        response.put("status", "OK");
        response.put("message", "Successful transfer");
        response.put("transfer", transfer);

        client.post().uri("/api/financial/account/transfer")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(transfer)
                .exchange()
                .expectStatus().isOk()
                .expectBody()

                //Body evaluation test
                .consumeWith(r -> {
                    try {
                        JsonNode json = objectMapper.readTree(r.getResponseBody());
                        assertEquals("OK", json.path("status").asText());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                })

                //or without .consumeWith
                .jsonPath("$.date").value(is(LocalDate.now().toString()))
                .jsonPath("$.status").value(v -> assertEquals("OK", v))
                .jsonPath("$.status").isEqualTo("OK")
                .jsonPath("$.message").isNotEmpty()
                .json(objectMapper.writeValueAsString(response));
    }
}