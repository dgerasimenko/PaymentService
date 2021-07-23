package com.dgerasimenko.service;

import com.dgerasimenko.entity.Payment;
import org.springframework.core.io.InputStreamResource;

import java.util.List;
import java.util.Optional;

public interface PaymentService {

    void create(Payment payment);

    List<Payment> findAll();

    Optional<Payment> findById(Long id);

    void update(Payment payment);

    void delete(Long id);

    byte[] exportCSV();
}
