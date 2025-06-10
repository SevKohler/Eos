package org.bih.eos.jpabase.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PersonVisitId implements Serializable {

    @Column(name = "ehr_id")
    private String ehr_id;

    @Column(name = "sourceVisit")
    private String sourceVisit;

    public PersonVisitId() {}

    public PersonVisitId(String ehr_id, String sourceVisit) {
        this.ehr_id = ehr_id;
        this.sourceVisit = sourceVisit;
    }

    public String getEhrId() {
        return ehr_id;
    }

    public String getSourceVisit() {
        return sourceVisit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersonVisitId that)) return false;
        return Objects.equals(ehr_id, that.ehr_id) &&
               Objects.equals(sourceVisit, that.sourceVisit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ehr_id, sourceVisit);
    }
}