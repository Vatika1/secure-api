package com.vatika.secureapi.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "audit_log")
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    protected AuditLog() {}

    public AuditLog(String message) {
        this.message = message;
    }

    public Long getId() { return id; }
    public String getMessage() { return message; }
}