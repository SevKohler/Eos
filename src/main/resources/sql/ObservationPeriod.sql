--Recalculate from visit occurrence
--Truncate table
truncate table @cdm_schema.observation_period;

--Create or reset a sequence
CREATE SEQUENCE IF NOT EXISTS @cdm_schema.observation_period_id_seq;
ALTER SEQUENCE @cdm_schema.observation_period_id_seq RESTART WITH 1;

--Period from min visit and max visit time
insert into @cdm_schema.observation_period (observation_period_id,
                                person_id,
                                observation_period_start_date,
                                observation_period_end_date,
                                period_type_concept_id)
select
    nextval('@cdm_schema.observation_period_id_seq') AS observation_period_id,
    vo.person_id AS person_id,
    min(vo.visit_start_date) AS observation_period_start_date,
    max(vo.visit_end_date) AS observation_period_end_date,
    32817 AS period_type_concept_id  --EHR
from @cdm_schema.visit_occurrence vo group by person_id;