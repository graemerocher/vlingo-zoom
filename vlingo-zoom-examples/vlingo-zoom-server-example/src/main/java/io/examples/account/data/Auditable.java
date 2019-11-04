package io.examples.account.data;

import java.sql.Timestamp;

public interface Auditable {

    Timestamp getDateCreated();

    void setDateCreated(Timestamp dateCreated);

    Timestamp getLastUpdated();

    void setLastUpdated(Timestamp lastUpdated);
}
