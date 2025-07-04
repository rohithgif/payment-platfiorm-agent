package com.grab.hackathon.repository;

import com.grab.hackathon.entity.GrabWallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GrabWalletRepository extends JpaRepository<GrabWallet, Long> {
}

