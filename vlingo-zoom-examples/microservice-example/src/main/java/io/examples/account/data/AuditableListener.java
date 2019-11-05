package io.examples.account.data;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.sql.Timestamp;
import java.time.Instant;

/**
 * The {@link AuditableListener} is an event handler for JPA entities that manages the {@link Auditable} updates
 * on a persisted entity.
 *
 * @author Kenny Bastani
 */
public class AuditableListener {

    /**
     * This method handles the pre-persist event that is dispatched from an {@link javax.persistence.EntityManager}.
     *
     * @param auditable is the {@link Auditable} entity that is subject to this created event.
     */
    @PrePersist
    void preCreate(Auditable auditable) {
        Timestamp now = Timestamp.from(Instant.now());
        auditable.setDateCreated(now);
        auditable.setLastUpdated(now);
    }

    /**
     * This method handles the pre-update event that is dispatched from an {@link javax.persistence.EntityManager}.
     *
     * @param auditable is the {@link Auditable} entity that is subject to this updated event.
     */
    @PreUpdate
    void preUpdate(Auditable auditable) {
        Timestamp now = Timestamp.from(Instant.now());
        auditable.setLastUpdated(now);
    }
}
