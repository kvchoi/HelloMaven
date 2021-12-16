package com.resilience;

import com.zaxxer.HikariCPTest;
import dev.failsafe.Failsafe;
import dev.failsafe.Fallback;
import dev.failsafe.RetryPolicy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Duration;

public class TestFailsafe {

    static final RetryPolicy<Object> retryPolicy = RetryPolicy.builder()
            .handle(Exception.class)
            .withDelay(Duration.ofSeconds(1))
            .withMaxRetries(3)
            .build();

    static final Fallback<Object> fallbackPolicy = Fallback.of(() -> System.out.println("=====fall back test======="));

    private static Connection connect() throws SQLException {
        return HikariCPTest.h2();
    }

    public static void main(String[] args) {
        // Run with retries before fallback
        try (Connection connection = Failsafe.with(fallbackPolicy).compose(retryPolicy).get(() -> connect())) {
            PreparedStatement ps = connection.prepareStatement("select 1");
            boolean result = ps.execute();
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("=====fail fast test=======");
    }
}
