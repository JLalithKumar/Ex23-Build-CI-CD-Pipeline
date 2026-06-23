package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppTest {

    @Test
    void testMessage() {
        String msg = "Payment Service Started";
        assertEquals("Payment Service Started", msg);
    }
}