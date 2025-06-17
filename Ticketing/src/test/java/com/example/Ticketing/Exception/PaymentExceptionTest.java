package com.example.Ticketing.Exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PaymentExceptionTest {
    @Test
    void testMessage() {
        PaymentException ex = new PaymentException("erro pagamento");
        assertEquals("erro pagamento", ex.getMessage());
    }
}
