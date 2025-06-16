package com.example.Ticketing.model.entity;

import com.example.Ticketing.Model.Entity.Reservation;
import com.example.Ticketing.Model.Entity.Product;
import com.example.Ticketing.Model.Entity.User;
import com.example.Ticketing.Model.Entity.Session;
import com.example.Ticketing.Model.Enum.ReservationStatus;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReservationTest {
    @Test
    void testGettersAndSetters() {
        Reservation reservation = new Reservation();
        Long id = 1L;
        User user = mock(User.class);
        Session session = mock(Session.class);
        int seatCount = 3;
        List<Product> products = Arrays.asList(mock(Product.class), mock(Product.class));
        LocalDateTime createdAt = LocalDateTime.now();
        ReservationStatus status = ReservationStatus.CANCELLED;
        LocalDateTime expirationTime = LocalDateTime.now().plusHours(1);

        reservation.setId(id);
        reservation.setUser(user);
        reservation.setSession(session);
        reservation.setSeatCount(seatCount);
        reservation.setProducts(products);
        reservation.setCreatedAt(createdAt);
        reservation.setStatus(status);
        reservation.setExpirationTime(expirationTime);

        assertEquals(id, reservation.getId());
        assertEquals(user, reservation.getUser());
        assertEquals(session, reservation.getSession());
        assertEquals(seatCount, reservation.getSeatCount());
        assertEquals(products, reservation.getProducts());
        assertEquals(createdAt, reservation.getCreatedAt());
        assertEquals(status, reservation.getStatus());
        assertEquals(expirationTime, reservation.getExpirationTime());
    }

    @Test
    void testDefaultValues() {
        Reservation reservation = new Reservation();
        assertNull(reservation.getId());
        assertNull(reservation.getUser());
        assertNull(reservation.getSession());
        assertEquals(0, reservation.getSeatCount());
        assertNotNull(reservation.getProducts());
        assertTrue(reservation.getProducts().isEmpty());
        assertNull(reservation.getCreatedAt());
        assertEquals(ReservationStatus.ACTIVE, reservation.getStatus());
        assertNull(reservation.getExpirationTime());
    }
}
