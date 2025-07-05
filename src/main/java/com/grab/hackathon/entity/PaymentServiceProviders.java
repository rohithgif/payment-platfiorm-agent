package com.grab.hackathon.entity;

import com.grab.hackathon.model.*;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
public class PaymentServiceProviders
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pspId;
    private int paymentOptionId;
    private int userId;
}
