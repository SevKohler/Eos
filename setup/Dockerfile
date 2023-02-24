FROM postgres:latest

COPY vocab/ /data/vocab
COPY ddls/create_schema.sql /docker-entrypoint-initdb.d/A_create_schema.sql
COPY ddls/OMOPCDM_postgresql_ddl.sql /docker-entrypoint-initdb.d/B_OMOPCDM_postgresql_ddl.sql
COPY ddls/OMOP_CDM_vocabulary_load_PostgreSQL.sql /docker-entrypoint-initdb.d/C_OMOP_CDM_vocabulary_load_PostgreSQL.sql
COPY ddls/OMOPCDM_postgresql_primary_keys.sql /docker-entrypoint-initdb.d/D_OMOPCDM_postgresql_primary_keys.sql
COPY ddls/OMOPCDM_postgresql_constraints.sql /docker-entrypoint-initdb.d/E_OMOPCDM_postgresql_constraints.sql
COPY ddls/OMOPCDM_postgresql_indices.sql /docker-entrypoint-initdb.d/F_OMOPCDM_postgresql_indices.sql
COPY ddls/create_sequences_and_ehr_to_person.sql /docker-entrypoint-initdb.d/G_create_sequences_and_ehr_to_person.sql
COPY ddls/replace_schema_variables.sh /data/
RUN  /data/replace_schema_variables.sh
