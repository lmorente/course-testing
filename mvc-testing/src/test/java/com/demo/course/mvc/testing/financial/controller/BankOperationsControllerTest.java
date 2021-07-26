package com.demo.course.mvc.testing.financial.controller;

import com.demo.course.mvc.testing.financial.data.FakeData;
import com.demo.course.mvc.testing.financial.model.DataTransferDTO;
import com.demo.course.mvc.testing.financial.service.BankOperationsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BankOperationsController.class)
class BankOperationsControllerTest {

    /*Mock driver testing controller with MockMvc*/

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BankOperationsService service;

    private static final FakeData data = new FakeData();

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp(){
        objectMapper = new ObjectMapper();
    }

    @Test
    void testDetails() throws Exception {
        //Given
        when(service.findAccountById(1L)).thenReturn(data.getAccount001());

        //When
        mvc.perform(get("/api/financial/account/1").contentType(MediaType.APPLICATION_JSON))

        //Then
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value("1"))
            .andExpect(jsonPath("$.person").value("Johnny Smith"))
            .andExpect(jsonPath("$.balance").value("12345.67"));

        verify(service).findAccountById(1L);
    }

    @Test
    void testGetBalanceAccount() throws Exception {
        //Given
        when(service.getBalanceAccount(2L)).thenReturn(data.getAccount002().getBalance());

        //When
        mvc.perform(get("/api/financial/account/balance/2").contentType(MediaType.APPLICATION_JSON))


                //Then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.balance").value("24567.21"));

        verify(service).getBalanceAccount(2L);
    }

    @Test
    void testTransfer() throws Exception {
        //Given
        DataTransferDTO dataTransfer = new DataTransferDTO(1L, 2L, 1L, new BigDecimal("250"));

        Map<String, Object> response = new HashMap<>();
        response.put("date", LocalDate.now().toString());
        response.put("status", "OK");
        response.put("message", "Successful transfer");
        response.put("transfer", dataTransfer);

        //When
        mvc.perform(post("/api/financial/account/transfer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dataTransfer)))

                //Then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("Successful transfer"))
                .andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
                .andExpect(content().json(objectMapper.writeValueAsString(response)));

        verify(service).transfer(1L, 2L, new BigDecimal("250"), 1L );
    }

    @Test
    void testGetTransferBank() throws Exception {
        //Given
        when(service.getTotalTransferBank(2L)).thenReturn(data.getBank002().getTotalTransfer());

        //When
        mvc.perform(get("/api/financial/bank/transfer/2").contentType(MediaType.APPLICATION_JSON))

                //Then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.balance").value("250"));

        verify(service).getTotalTransferBank(2L);
    }
}