package com.dgerasimenko.test.integration;

import com.dgerasimenko.PaymentServiceApplication;
import com.dgerasimenko.dao.PaymentRepository;
import com.dgerasimenko.entity.Payment;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PaymentServiceApplication.class)
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.JVM)
public class PaymentsCRUDTest {

    private static final String BASE_URL = "/payments";

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void findByIdTest() throws Exception {
        mockMvc.perform(get(BASE_URL + "/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.amount").value(10.5))
                .andExpect(jsonPath("$.currency").value("USD"))
                .andExpect(jsonPath("$.userId").value(111))
                .andExpect(jsonPath("$.targetBankAccountNumber").value("AAA1234BBB56789"));
    }

    @Test
    public void findAllTest() throws Exception {
        mockMvc.perform(get(BASE_URL)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].amount").value(10.5))
                .andExpect(jsonPath("$[0].currency").value("USD"))
                .andExpect(jsonPath("$[0].userId").value(111))
                .andExpect(jsonPath("$[0].targetBankAccountNumber").value("AAA1234BBB56789"));
    }

    @Test
    public void createPaymentTest() throws Exception {
        String newTargetBankAccountNumber = "EEE1234FFF56789";
        List<Payment> payments = paymentRepository.findAll();
        Assertions.assertNotNull(payments);
        Assertions.assertEquals(3, payments.size());
        Assertions.assertFalse(payments.stream().filter(p->p.getTargetBankAccountNumber().equals(newTargetBankAccountNumber)).findAny().isPresent());

        String payload = "{\n" +
                "    \"amount\": 100.50,\n" +
                "    \"currency\": \"UAH\",\n" +
                "    \"userId\": 444,\n" +
                "    \"targetBankAccountNumber\": \"EEE1234FFF56789\"\n" +
                "}";
        mockMvc.perform(post(BASE_URL)
                .content(payload)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        payments = paymentRepository.findAll();
        Assertions.assertNotNull(payments);
        Assertions.assertEquals(4, payments.size());
        Assertions.assertTrue(payments.stream().filter(p->p.getTargetBankAccountNumber().equals(newTargetBankAccountNumber)).findAny().isPresent());

    }

    @Test
    public void updatePaymentTest() throws Exception {
        Optional<Payment> payment = paymentRepository.findById(1L);
        Assertions.assertTrue(payment.isPresent());
        Assertions.assertEquals("AAA1234BBB56789", payment.get().getTargetBankAccountNumber());

        String newTargetBankAccountNumber = "BBB1234CCC56789";
        String payload = "    {\n" +
                "        \"id\": 1,\n" +
                "        \"amount\": 10.50,\n" +
                "        \"currency\": \"USD\",\n" +
                "        \"userId\": 111,\n" +
                "        \"targetBankAccountNumber\": \"" + newTargetBankAccountNumber + "\"\n" +
                "    }";
        mockMvc.perform(put(BASE_URL)
                .content(payload)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        payment = paymentRepository.findById(1L);
        Assertions.assertTrue(payment.isPresent());
        Assertions.assertEquals(newTargetBankAccountNumber, payment.get().getTargetBankAccountNumber());
    }

    @Test
    public void deletePaymentTest() throws Exception {
        Optional<Payment> deletedPayment = paymentRepository.findById(1L);
        Assertions.assertTrue(deletedPayment.isPresent());

        mockMvc.perform(delete(BASE_URL + "/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        deletedPayment = paymentRepository.findById(1L);
        Assertions.assertFalse(deletedPayment.isPresent());
    }

    @Test
    public void expotCSVTest() throws Exception {
        mockMvc.perform(get(BASE_URL + "/exportCSV")
                .accept("text/csv"))
                .andExpect(status().isOk());
    }
}
