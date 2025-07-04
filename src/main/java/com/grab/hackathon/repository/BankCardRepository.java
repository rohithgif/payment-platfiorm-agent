package com.grab.hackathon.repository;

import com.grab.hackathon.entity.BankCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankCardRepository extends JpaRepository<BankCard, Long> {
}

