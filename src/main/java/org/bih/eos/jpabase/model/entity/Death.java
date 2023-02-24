package org.bih.eos.jpabase.model.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="death")
@org.hibernate.annotations.GenericGenerator(name = "person-primarykey",
        strategy = "foreign", parameters = { @org.hibernate.annotations.Parameter(name = "property", value = "person") })
public class Death extends JPABaseEntity {
    @Id
    @GeneratedValue(generator = "person-primarykey")
    @Column(name = "person_id")
    private long id;

    @OneToOne
    @PrimaryKeyJoinColumn(name = "person_id")
    private Person person;

    @Column(name="death_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date deathDate;

    @Column(name="death_datetime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date deathDateTime;

    @ManyToOne
    @JoinColumn(name = "death_type_concept_id")
    private Concept deathTypeConceptId;

    @ManyToOne
    @JoinColumn(name = "cause_concept_id")
    private Concept causeConceptId;

    @ManyToOne
    @JoinColumn(name = "cause_source_concept_id")
    private Concept causeSourceConceptId;

    @Column(name = "cause_source_value")
    private String causeSourceValue;

    public Death() {
    }

    public Death(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Date getDeathDate() {
        return deathDate;
    }

    public void setDeathDate(Date deathDate) {
        this.deathDate = deathDate;
    }

    public Date getDeathDateTime() {
        return deathDateTime;
    }

    public void setDeathDateTime(Date deathDateTime) {
        this.deathDateTime = deathDateTime;
    }

    public Concept getDeathTypeConceptId() {
        return deathTypeConceptId;
    }

    public void setDeathTypeConceptId(Concept deathTypeConceptId) {
        this.deathTypeConceptId = deathTypeConceptId;
    }

    public Concept getCauseConceptId() {
        return causeConceptId;
    }

    public void setCauseConceptId(Concept causeConceptId) {
        this.causeConceptId = causeConceptId;
    }

    public Concept getCauseSourceConceptId() {
        return causeSourceConceptId;
    }

    public void setCauseSourceConceptId(Concept causeSourceConceptId) {
        this.causeSourceConceptId = causeSourceConceptId;
    }

    public String getCauseSourceValue() {
        return causeSourceValue;
    }

    public void setCauseSourceValue(String cause_source_value) {
        this.causeSourceValue = cause_source_value;
    }

    @Override
    public Long getIdAsLong() {
        return getId();
    }

}
