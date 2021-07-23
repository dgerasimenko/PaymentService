package com.dgerasimenko.controller;

import com.dgerasimenko.configuration.ApplicationConfiguration;
import com.dgerasimenko.entity.Payment;
import com.dgerasimenko.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentController.class);

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping
    public ResponseEntity<List<Payment>> findAll() {
        LOGGER.debug("Received findAll request");
        final List<Payment> payments = paymentService.findAll();

        return payments != null && !payments.isEmpty()
                ? new ResponseEntity<>(payments, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> findById(@PathVariable(name = "id") Long id) {
        LOGGER.debug("Received findById:{} request", id);
        final Optional<Payment> payment = paymentService.findById(id);

        return payment.isPresent()
                ? new ResponseEntity<>(payment.get(), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Payment payment) {
        LOGGER.debug("Received create request. New payment item:{}", payment);

        paymentService.create(payment);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Payment payment) {
        LOGGER.debug("Received update request. Updated payment item:{}", payment);

        paymentService.update(payment);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Resource> delete(@PathVariable(name = "id") Long id) {
        LOGGER.debug("Received delete request. Payment Id:{}", id);
        paymentService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/exportCSV", produces = "text/csv")
    public ResponseEntity<InputStreamResource> exportCSV() {
        LOGGER.debug("Received exportCSV request.");

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=payments.csv");
        headers.set(HttpHeaders.CONTENT_TYPE, "text/csv");

        return new ResponseEntity<>(
                new InputStreamResource(new ByteArrayInputStream(paymentService.exportCSV())),
                headers,
                HttpStatus.OK
        );
    }
}

