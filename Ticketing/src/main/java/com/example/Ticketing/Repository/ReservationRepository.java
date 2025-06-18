package com.example.Ticketing.Repository;

import com.example.Ticketing.Model.Entity.Reservation;
import com.example.Ticketing.Model.Enum.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByUserId(Long userId);

    List<Reservation> findByStatusAndExpirationTimeBefore(ReservationStatus active, LocalDateTime now);
}