package com.grab.hackathon.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "user")
public class User {
    @Id
    @Column(name = "u_id")
    private Long userId;

    @Column(name = "u_name")
    private String userName;

    @Column(name = "grab_wallet_gw_id")
    private Long grabWalletGwId;

    @Column(name = "id", nullable = false)
    private Long internalId;

    @Column(name = "name")
    private String name;
}

