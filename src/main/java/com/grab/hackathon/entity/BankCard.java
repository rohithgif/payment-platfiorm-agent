package com.grab.hackathon.entity;

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
public class BankCard {
    @Id
    @GeneratedValue
    private Long bankCardId;

    private String bankCardNumber;
    private LocalDate endingDate;
    private String cvv;
    private Double cashbackOffered;

    @ManyToOne
    private Bank bank;
}

