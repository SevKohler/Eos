package org.bih.eos.jpabase.model.entity;
import java.io.Serializable;

public class CompositeKey implements Serializable {
    private Long domainConceptId1;
    private Long factId1;
    private Long domainConceptId2;
    private Long factId2;
    private Concept relationshipConcept;
}
