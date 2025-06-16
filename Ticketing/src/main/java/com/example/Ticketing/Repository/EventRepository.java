package com.example.Ticketing.Repository;

import com.example.Ticketing.Model.Entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
    boolean existsByName(String name);
    Page<Event> findAll(Pageable pageable);
}
