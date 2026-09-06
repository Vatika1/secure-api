package com.vatika.secureapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.time.Duration;
import java.util.List;


@RestController
@RequiredArgsConstructor
public class OrderController {

    public record Order(long id, String clientId, String item) implements Serializable {}

    private final StringRedisTemplate redis;

    private static final List<Order> ORDERS = List.of(
            new Order(1, "acme", "laptop"),
            new Order(2, "acme", "monitor"),
            new Order(3, "globex", "keyboard"));


    @Cacheable(value = "orders", key = "#authentication.name")
    @GetMapping("/orders")
    public List<Order> getOrders(Authentication authentication) {
        boolean staff = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_STAFF"));
        if (staff) {
            return ORDERS;
        }
        String clientId = (String) authentication.getDetails();
        return ORDERS.stream().filter(o -> o.clientId().equals(clientId)).toList();
    }

    @PostMapping("/orders")
    public ResponseEntity<String> create(@RequestHeader("Idempotency-Key") String key,
                                         @RequestBody String body) {
        Boolean first = redis.opsForValue().setIfAbsent("idem:" + key, body, Duration.ofMinutes(10));
        if (Boolean.FALSE.equals(first)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("duplicate");
        }
        return ResponseEntity.ok("created");
    }
}