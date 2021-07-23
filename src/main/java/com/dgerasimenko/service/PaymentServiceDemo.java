package com.dgerasimenko.service;

import com.dgerasimenko.dao.PaymentRepository;
import com.dgerasimenko.entity.Payment;
import com.dgerasimenko.utils.CSVUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaymentServiceDemo implements PaymentService {

    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentServiceDemo(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public void create(Payment payment) {
        paymentRepository.save(payment);
    }

    @Override
    public List<Payment> findAll() {
        return paymentRepository.findAll();
    }

    @Override
    public Optional<Payment> findById(Long id) {
        return paymentRepository.findById(id);
    }

    @Override
    public void update(Payment payment) {
        paymentRepository.save(payment);
    }

    @Override
    public void delete(Long id) {
        paymentRepository.deleteById(id);
    }

    @Override
    public byte[] exportCSV() {
        final List<Payment> payments = paymentRepository.findAll();
        return CSVUtils.convertToCSVStream(() -> new String[]{"id", "amount", "currency", "userId", "targetBankAccountNumber"},
                () -> payments.stream().map(payment -> {
                    List<String> fields = new ArrayList<>(5);
                    fields.add(payment.getId().toString());
                    fields.add(payment.getAmount().toString());
                    fields.add(payment.getCurrency());
                    fields.add(payment.getUserId().toString());
                    fields.add(payment.getTargetBankAccountNumber());
                    return fields;
                }).collect(Collectors.toList()));
    }
}
