package com.vatika.secureapi.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderController {

    public record Order(long id, String clientId, String item) {}

    private static final List<Order> ORDERS = List.of(
            new Order(1, "acme", "laptop"),
            new Order(2, "acme", "monitor"),
            new Order(3, "globex", "keyboard"));

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
}