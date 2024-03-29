package io.examples.account.data;

import io.micronaut.data.annotation.DateCreated;
import io.micronaut.data.annotation.DateUpdated;

import javax.persistence.MappedSuperclass;
import java.sql.Timestamp;

/**
 * The {@link BaseEntity} describes an {@link Auditable} JPA entity that is mapped to a concrete class that extends
 * this implementation.
 *
 * @author Kenny Bastani
 * @see Auditable
 */
@MappedSuperclass
public class BaseEntity implements Auditable {

    @DateCreated
    private Timestamp dateCreated;

    @DateUpdated
    private Timestamp lastUpdated;

    /**
     * Get the {@link Timestamp} that describes when an auditable entity was created and saved.
     *
     * @return a {@link Timestamp}.
     */
    @Override
    public Timestamp getDateCreated() {
        return dateCreated;
    }

    /**
     * Set the {@link Timestamp} that describes when an auditable entity is created and saved.
     *
     * @param dateCreated is the {@link Timestamp} for when an auditable entity is created and saved.
     */
    @Override
    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    /**
     * Get the {@link Timestamp} that describes when an auditable entity was last updated and saved.
     *
     * @return a {@link Timestamp}.
     */
    @Override
    public Timestamp getLastUpdated() {
        return lastUpdated;
    }

    /**
     * Set the {@link Timestamp} that describes when an auditable entity is updated and saved.
     *
     * @param lastUpdated is the {@link Timestamp} for when an auditable entity is updated and saved.
     */
    @Override
    public void setLastUpdated(Timestamp lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
