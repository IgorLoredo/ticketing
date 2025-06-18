package com.example.Ticketing.Repository;

import com.example.Ticketing.Model.Entity.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PaymentRepository  extends JpaRepository<Payment, Long> {

    boolean existsByIdempotencyKey(String idempotencyKey);

    Optional<Payment> findByIdempotencyKey(String idempotencyKey);


        @Query("SELECT p FROM Payment p WHERE p.reservation.user.id = :userId")
        Page<Payment> findByUserId(@Param("userId") Long userId, Pageable pageable);

}