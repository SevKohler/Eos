CREATE Sequence @cdmDatabaseSchema.location_id_seq start 1;
CREATE Sequence @cdmDatabaseSchema.caresite_id_seq start 1;
CREATE Sequence @cdmDatabaseSchema.condition_occurrence_id_seq start 1;
CREATE Sequence @cdmDatabaseSchema.device_exposure_id_seq start 1;
CREATE Sequence @cdmDatabaseSchema.drug_exposure_id_seq start 1;
CREATE Sequence @cdmDatabaseSchema.measurement_id_seq start 1;
CREATE Sequence @cdmDatabaseSchema.note_id_seq start 1;
CREATE Sequence @cdmDatabaseSchema.person_id_seq start 1;
CREATE Sequence @cdmDatabaseSchema.observation_id_seq start 1;
CREATE Sequence @cdmDatabaseSchema.provider_id_seq start 1;
CREATE Sequence @cdmDatabaseSchema.specimen_id_seq start 1;
CREATE Sequence @cdmDatabaseSchema.visit_detail_id_seq start 1;
CREATE Sequence @cdmDatabaseSchema.visit_occurrence_id_seq start 1;
CREATE Sequence @cdmDatabaseSchema.procedure_occurrence_id_seq start 1;
-- Table: public.ehr_to_person

-- DROP TABLE IF EXISTS public.ehr_to_person;

CREATE TABLE IF NOT EXISTS @cdmDatabaseSchema.ehr_to_person
(
    ehr_id character varying(255) COLLATE pg_catalog."default" NOT NULL,
    person_id integer NOT NULL,
    CONSTRAINT ehr_to_person_pkey PRIMARY KEY (ehr_id),
    CONSTRAINT fpk_ehr_to_person_person_id FOREIGN KEY (person_id)
    REFERENCES @cdmDatabaseSchema.person (person_id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS @cdmDatabaseSchema.ehr_to_person
    OWNER to postgres;
