INSERT INTO Users (id, name, email, CREATED_AT) VALUES
  (1, 'Alice Johnson', 'alice@example.com', CURRENT_TIMESTAMP),
  (2, 'Bob Smith', 'bob@example.com', CURRENT_TIMESTAMP),
  (3, 'Carol White', 'carol@example.com', CURRENT_TIMESTAMP);

  INSERT INTO Event (id, name, created_at, updated_at) VALUES
    (1, 'Cinema Night', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (2, 'Tech Conference', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (3, 'Jazz Concert', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- SESSIONS
INSERT INTO Session (id, name, start_Time, created_At, updated_At, event_id, total_Seats, seat_Price, available_Seats) VALUES
  (1, 'Avengers Screening', '2025-06-20 19:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 100, 25.00, 97),
  (2, 'Kotlin Workshop', '2025-06-22 09:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, 50, 120.00, 47),
  (3, 'Smooth Jazz Night', '2025-06-25 21:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 3, 80, 60.00, 75);

-- PRODUCTS
INSERT INTO Product (id, name, price, created_At) VALUES
  (1, 'Popcorn', 10.00, CURRENT_TIMESTAMP),
  (2, 'Soda', 5.00, CURRENT_TIMESTAMP),
  (3, 'Candy', 3.50, CURRENT_TIMESTAMP);


-- Em vez de 'CONFIRMED', use 'ACTIVE' por exemplo
INSERT INTO Reservation (id, user_id, session_id, seat_Count, created_At, status, expiration_Time) VALUES
  (1, 1, 1, 2, CURRENT_TIMESTAMP, 'ACTIVE', TIMESTAMPADD(MINUTE, 15, CURRENT_TIMESTAMP)),
  (2, 2, 2, 1, CURRENT_TIMESTAMP, 'ACTIVE', TIMESTAMPADD(MINUTE, 15, CURRENT_TIMESTAMP)),
  (3, 3, 3, 3, CURRENT_TIMESTAMP, 'ACTIVE', TIMESTAMPADD(MINUTE, 15, CURRENT_TIMESTAMP)); -- aqui ainda vai falhar

-- RESERVATION_PRODUCTS (many-to-many)
INSERT INTO reservation_products (reservation_id, product_id) VALUES
  (1, 1), -- Alice bought popcorn
  (1, 2), -- and soda
  (2, 2), -- Bob bought soda
  (3, 1), -- Carol bought popcorn
  (3, 3); -- and candy

-- PAYMENTS
INSERT INTO Payment (id, reservation_id, method, idempotency_Key, transaction_Id, amount, created_At, status) VALUES
  (1, 1, 'CREDIT_CARD', 'key-001', 'txn-abc-001', 55.00, CURRENT_TIMESTAMP, 'COMPLETED'),
  (2, 2, 'PIX', 'key-002', 'txn-abc-002', 125.00, CURRENT_TIMESTAMP, 'COMPLETED'),
  (3, 3, 'DEBIT_CARD', 'key-003', 'txn-abc-003', 85.00, CURRENT_TIMESTAMP, 'FAILED');