package com.grab.hackathon.model;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OffersDetails {
    private String offerId;
    private String description;
    private double cashbackAmount;
}
