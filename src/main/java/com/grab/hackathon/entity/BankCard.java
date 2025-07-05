package com.grab.hackathon.entity;

import com.grab.hackathon.model.PaymentMethod;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
public class BankCard extends PaymentMethod {
    @Id
    @GeneratedValue
    private Long bankCardId;

    private String bankCardNumber;
    private LocalDate endingDate;
    private String cvv;
    private String cardType;

    @ManyToOne
    private Bank bank;

    private Boolean cashbackOffered;
}

