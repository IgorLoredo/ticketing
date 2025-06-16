package com.example.Ticketing.Repository;

import com.example.Ticketing.Model.Entity.Event;
import com.example.Ticketing.Model.Entity.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session, Long> {
    boolean existsByEventIdAndName(Long eventId, String name);
    Page<Session> findByEventId(Long eventId, Pageable pageable);
}