package com.grab.hackathon.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
public class User {
    @Id @GeneratedValue
    private Long id;
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    private GrabWallet grabWallet;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<BankCard> bankCards;

    @OneToOne(cascade = CascadeType.ALL)
    private PayPalAccount payPal;

    @OneToOne(cascade = CascadeType.ALL)
    private NeftAccount neftAccount;
}

