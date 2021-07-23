package com.dgerasimenko.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private BigDecimal amount;
    private String currency;
    private Long userId;
    private String targetBankAccountNumber;

    public Payment() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTargetBankAccountNumber() {
        return targetBankAccountNumber;
    }

    public void setTargetBankAccountNumber(String targetBankAccountNumber) {
        this.targetBankAccountNumber = targetBankAccountNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return Objects.equals(id, payment.id) && Objects.equals(amount, payment.amount) && Objects.equals(currency, payment.currency) && Objects.equals(userId, payment.userId) && Objects.equals(targetBankAccountNumber, payment.targetBankAccountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, currency, userId, targetBankAccountNumber);
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", userId='" + userId + '\'' +
                ", targetBankAccountNumber='" + targetBankAccountNumber + '\'' +
                '}';
    }
}
