package com.grab.hackathon.repository;

import com.grab.hackathon.entity.Payment;
import com.grab.hackathon.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByUser(User user); // Optional custom method
}