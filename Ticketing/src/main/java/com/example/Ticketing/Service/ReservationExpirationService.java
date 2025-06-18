package com.example.Ticketing.Service;

import com.example.Ticketing.Model.Entity.Reservation;
import com.example.Ticketing.Model.Entity.Session;
import com.example.Ticketing.Model.Enum.ReservationStatus;
import com.example.Ticketing.Repository.ReservationRepository;
import com.example.Ticketing.Repository.SessionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationExpirationService {

    private static final Logger log = LoggerFactory.getLogger(ReservationExpirationService.class);

    private final ReservationRepository reservationRepository;
    private final SessionRepository sessionRepository;

    public ReservationExpirationService(ReservationRepository reservationRepository, SessionRepository sessionRepository) {
        this.reservationRepository = reservationRepository;
        this.sessionRepository = sessionRepository;
    }

    @Scheduled(fixedRate = 60000) // Executa a cada minuto
    @Transactional
    public void expireReservations() {
        LocalDateTime now = LocalDateTime.now();
        log.info("Running scheduled task to expire reservations at {}", now);

        List<Reservation> expired = reservationRepository
                .findByStatusAndExpirationTimeBefore(ReservationStatus.ACTIVE, now);

        if (expired.isEmpty()) {
            log.info("No expired reservations found");
            return;
        }

        log.info("Found {} expired reservations", expired.size());

        for (Reservation reservation : expired) {
            try {
                Session session = reservation.getSession();
                int newAvailability = session.getAvailableSeats() + reservation.getSeatCount();
                session.setAvailableSeats(newAvailability);
                sessionRepository.save(session);

                reservation.setStatus(ReservationStatus.EXPIRED);

                log.info("Reservation {} expired. Freed {} seats for session {}",
                        reservation.getId(),
                        reservation.getSeatCount(),
                        session.getId());

            } catch (Exception e) {
                log.error("Error expiring reservation {}: {}", reservation.getId(), e.getMessage());
            }
        }

        reservationRepository.saveAll(expired);
        log.info("Expired reservations updated successfully.");
    }
}
