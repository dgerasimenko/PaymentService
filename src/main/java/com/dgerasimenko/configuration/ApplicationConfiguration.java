package com.dgerasimenko.configuration;

import com.dgerasimenko.dao.PaymentRepository;
import com.dgerasimenko.entity.Payment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class ApplicationConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationConfiguration.class);

    @Bean
    CommandLineRunner initDatabase(PaymentRepository repository) {
        return args -> {
            Payment payment1 = new Payment();
            payment1.setUserId(111L);
            payment1.setTargetBankAccountNumber("AAA1234BBB56789");
            payment1.setAmount(new BigDecimal(10.5));
            payment1.setCurrency("USD");
            LOGGER.info("Preloading " + repository.save(payment1));

            Payment payment2 = new Payment();
            payment2.setUserId(111L);
            payment2.setTargetBankAccountNumber("BBB1234CCC56789");
            payment2.setAmount(new BigDecimal(20.5));
            payment2.setCurrency("EUR");
            LOGGER.info("Preloading " + repository.save(payment2));

            Payment payment3 = new Payment();
            payment3.setUserId(333L);
            payment3.setTargetBankAccountNumber("CCC1234DDD56789");
            payment3.setAmount(new BigDecimal(30.5));
            payment3.setCurrency("GBP");
            LOGGER.info("Preloading " + repository.save(payment3));
        };
    }
}
