package com.demo.course.mvc.testing.financial.controller;

import com.demo.course.mvc.testing.financial.model.Account;
import com.demo.course.mvc.testing.financial.model.DataTransferDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BankOperationsControllerRestTemplateTest {

    /*Important: The integration tests do not read mocked data but those of the DB,
     *so they can fail with the DB modifications.
     *
     * One possible solution is to use @Order (number)*/

    @Autowired
    private TestRestTemplate client;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp(){
        objectMapper = new ObjectMapper();
    }

    @LocalServerPort
    private int port;

    @Test
    @Order(1)
    void testTransfer() throws JsonProcessingException {
        DataTransferDTO data = new DataTransferDTO(1L, 2L, 1L, new BigDecimal("500"));

        /*ResponseEntity<String> response = client.postForEntity("http://localhost:" + port "/api/financial/account/transfer",
                data, String.class);*/
        ResponseEntity<String> response = client.postForEntity("/api/financial/account/transfer", data, String.class);

        String json = response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(json);
        assertTrue(json.contains("Successful transfer"));
        assertTrue(json.contains("{\"origin\":1,\"destination\":2,\"bank\":1,\"amount\":500}"));

        JsonNode jsonNode = objectMapper.readTree(json);
        assertEquals("Successful transfer", jsonNode.path("message").asText());
        assertEquals(500L, jsonNode.path("transfer").path("amount").asLong());
    }

    @Test
    @Order(2)
    void testGetBalanceAccount() throws JsonProcessingException {
        ResponseEntity<String> response = client.getForEntity("/api/financial/account/balance/3", String.class);
        JsonNode jsonNode = objectMapper.readTree(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        assertEquals("645345.23", jsonNode.path("balance").asText());
    }

    @Test
    @Order(3)
    void testDetails() throws JsonProcessingException {
        ResponseEntity<Account> response = client.getForEntity("/api/financial/account/3", Account.class);
        Account account = response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        assertEquals(new Account(3L, "Mia Mirren", new BigDecimal("645345.23")), account);
    }

    @Test
    @Order(4)
    void testGetTransferBank() throws JsonProcessingException {
        ResponseEntity<String> response = client.getForEntity("/api/financial/bank/transfer/2", String.class);
        JsonNode jsonNode = objectMapper.readTree(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        assertEquals(3000L, jsonNode.path("balance").asLong());
    }
}