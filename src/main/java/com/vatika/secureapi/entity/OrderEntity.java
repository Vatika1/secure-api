package com.vatika.secureapi.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String clientId;
    private String item;

    protected OrderEntity() {}

    public OrderEntity(String clientId, String item) {
        this.clientId = clientId;
        this.item = item;
    }

    public Long getId() { return id; }
    public String getClientId() { return clientId; }
    public String getItem() { return item; }
}