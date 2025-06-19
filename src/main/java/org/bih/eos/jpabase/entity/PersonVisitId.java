package org.bih.eos.jpabase.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PersonVisitId implements Serializable {

    @Column(name = "ehr_id")
    private String ehrId;

    @Column(name = "sourceVisit")
    private String sourceVisit;

    public PersonVisitId() {}

    public PersonVisitId(String ehr_id, String sourceVisit) {
        this.ehrId = ehr_id;
        this.sourceVisit = sourceVisit;
    }

    public String getEhrId() {
        return ehrId;
    }

    public String getSourceVisit() {
        return sourceVisit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersonVisitId that)) return false;
        return Objects.equals(ehrId, that.ehrId) &&
               Objects.equals(sourceVisit, that.sourceVisit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ehrId, sourceVisit);
    }
}