package io.examples.account.data;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.sql.Timestamp;

@MappedSuperclass
@EntityListeners(AuditableListener.class)
public class BaseEntity implements Auditable {

    private Timestamp dateCreated;
    private Timestamp lastUpdated;

    @Override
    public Timestamp getDateCreated() {
        return dateCreated;
    }

    @Override
    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public Timestamp getLastUpdated() {
        return lastUpdated;
    }

    @Override
    public void setLastUpdated(Timestamp lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
