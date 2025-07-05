package com.grab.hackathon.model;

import jakarta.persistence.*;

@MappedSuperclass
public abstract class PaymentMethod extends UserPaymentOptionDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String provider;

}

