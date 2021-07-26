package com.demo.course.mvc.testing.financial.controller;

import com.demo.course.mvc.testing.financial.exception.BankException;
import com.demo.course.mvc.testing.financial.model.Account;
import com.demo.course.mvc.testing.financial.model.DataTransferDTO;
import com.demo.course.mvc.testing.financial.service.BankOperationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("api/financial")
public class BankOperationsController {

    @Autowired
    BankOperationsService service;

    private static final String BANK_BASE = "/bank";
    private static final String ACCOUNT_BASE = "/account";

    @GetMapping(ACCOUNT_BASE + "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Account detailAccount(@PathVariable Long id){
        return service.findAccountById(id);
    }

    @GetMapping(ACCOUNT_BASE + "/balance/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity getBalanceAcccount(@PathVariable Long id){
        Map<String, Object> response = new HashMap<>();
        response.put("date", LocalDate.now().toString());
        response.put("status", "OK");
        response.put("balance", service.getBalanceAccount(id));
        return ResponseEntity.ok(response);
    }

    @PostMapping(ACCOUNT_BASE + "/transfer")
    public ResponseEntity transfer(@RequestBody DataTransferDTO transferDTO) {
        if(Objects.nonNull(transferDTO)) {
            try {
                service.transfer(transferDTO.getOrigin(), transferDTO.getDestination(),
                        transferDTO.getAmount(), transferDTO.getBank());

                Map<String, Object> response = new HashMap<>();
                response.put("date", LocalDate.now().toString());
                response.put("status", "OK");
                response.put("message", "Successful transfer");
                response.put("transfer", transferDTO);
                return ResponseEntity.ok(response);

            } catch (BankException e){
                return (ResponseEntity) ResponseEntity.unprocessableEntity();
            }
        }
        return (ResponseEntity) ResponseEntity.badRequest();
    }

    @GetMapping(BANK_BASE + "/transfer/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity getTransferBank(@PathVariable Long id){
        Map<String, Object> response = new HashMap<>();
        response.put("date", LocalDate.now().toString());
        response.put("status", "OK");
        response.put("balance", service.getTotalTransferBank(id));
        return ResponseEntity.ok(response);
    }
}
