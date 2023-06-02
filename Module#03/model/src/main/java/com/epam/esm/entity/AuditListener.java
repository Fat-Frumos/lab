package com.epam.esm.entity;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class AuditListener {

    /**
     * Pre-persist hook to set the entity date.
     * <p>
     * Callback method invoked before the entity is persisted.
     * It sets the create and last update dates to the current timestamp.
     */
    @PrePersist
    public void prePersist(Object entity) {
        if (entity instanceof Order) {
            Order order = (Order) entity;
            order.setOrderDate(now());
        }
        if (entity instanceof Certificate) {
            Certificate certificate = (Certificate) entity;
            certificate.setCreateDate(now());
            certificate.setLastUpdateDate(now());
        }
    }

    /**
     * Callback method invoked before the entity is updated.
     * It updates the last update date to the current timestamp
     */
    @PreUpdate
    public void preUpdate(Object entity) {
        if (entity instanceof Certificate) {
            Certificate certificate = (Certificate) entity;
            certificate.setLastUpdateDate(now());
        }
    }

    private static Timestamp now() {
        return Timestamp.valueOf(LocalDateTime.now());
    }
}
