package org.bih.eos.jpabase.entity;
import java.io.Serializable;
import java.util.Objects;

public class CompositeKey implements Serializable {
    private Long domainConceptId1;
    private Long factId1;
    private Long domainConceptId2;
    private Long factId2;
    private Concept relationshipConcept;
    
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompositeKey that = (CompositeKey) o;
        return Objects.equals(domainConceptId1, that.domainConceptId1) &&
               Objects.equals(factId1, that.factId1) &&
               Objects.equals(domainConceptId2, that.domainConceptId2) &&
               Objects.equals(factId2, that.factId2) &&
               Objects.equals(relationshipConcept, that.relationshipConcept);
    }

    @Override
    public int hashCode() {
        return Objects.hash(domainConceptId1, factId1, domainConceptId2, factId2, relationshipConcept);
    }
}
