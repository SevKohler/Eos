Eos
====

Note
------
Eos is the result of a research project. To support our research, please cite one of our papers instead of referencing our github in scientific articles. You can find an overview of papers about Eos here. If you are not sure which paper to cite, we recommend this one:

Severin Kohler, Diego Boscá, Florian Kärcher, Birger Haarbrandt, Manuel Prinz, Michael Marschollek, Roland Eils, Eos and OMOCL: Towards a seamless integration of openEHR records into the OMOP Common Data Model, Journal of Biomedical Informatics, Volume 144, 2023 [(Link)](https://doi.org/10.1016/j.jbi.2023.104437)


Thanks!

Introduction
------
![connectEHR](https://user-images.githubusercontent.com/5692615/234679600-432ab931-4561-48fe-8521-1bd57955b3bb.svg)

## Setup

### Prerequisites

* JDK (>= 17)
* Apache Maven (>= 3.8.0)
* docker and docker-compose if you want to use docker
* Postgres if not using docker
* optionally: openEHR platform, otherwise there is the option to setup one down below

### Setting up Eos

1. clone repo `git clone --recursive` (may require [SSH authentication to github](https://docs.github.com/en/authentication/connecting-to-github-with-ssh))
2. Use Common Data model sqls from `setup/ddls/` or download
   them [here](https://github.com/OHDSI/CommonDataModel/tree/master/inst/ddl/5.4/postgresql). If downloaded change the file names according to the provided ones (delete the version number).
3. Download Vocabs from https://athena.ohdsi.org/, you need to create an Account for that, afterwards copy them
   to `setup/vocab/`. For recommended vocabs see [Wiki](https://github.com/SevKohler/Eos/wiki/Recommended-Vocabs).

### With Docker

4. Check if the OMOP_CDM_vocabulary_load sql script contains all your csvs.
5. choose a docker-compose profile of your liking which can be found in `setup/`.<br>
For the entire stack (including the open source EHRBase openEHR CDR) run: `docker-compose --profile eos --profile cdm --profile ehrbase up`.<br>
**Setup may take a while since all the vocabularies are loaded into the database**

### Without Docker

4. Start your postgres database (in this walktrough the example port is **5433**, so it is not in conflict with other
   postgres dbs from e.g. the openEHR platform)

5. execute the command in `setup/ddls`

```shell script
   $ psql -h localhost --port=5433 -U postgres -W --dbname=YOUR_DB_NAME --file=db_setup.sql
   ```

you can empty the folder `setup/vocab` afterwards if you wish 


6. Build the application

```shell script
$ mvn clean install
```

7. configure database in `src/main/resources/application.yml`

```yml
   datasource:
     password: YOUR_PASSWORD
     username: YOUR_USERNAME
     url: jdbc:postgresql://localhost:5433/YOUR_SCHEMA #default is public
```

8. configure openEHR platform in `src/main/resources/application.yml`

```yml
  ehrbase:
    base-url: YOUR_PLATFORM_URL
    security:
      type: basic # you can also use other security depending on the capabilities of your CDR
      user:
        name: YOUR_USERNAME
        password: YOUR_PASSWORD
```

9. configure cronjob for eras or leave them for manual execution

10. Run the application

## Using Eos
Check the wiki for [available api calls](https://github.com/SevKohler/Eos/wiki/API-endpoints) or use the **POSTMAN** example collection found in `.config/`.
0. Make sure there's an EHR in the CDR with at least one COMPOSITION. (For openEHR getting started consider https://docs.ehrbase.org/docs/category/openehr-introduction)
1. Create PERSONs in the OMOP database for each (or a specific) by POSTing to EOS's '/person' endpoint with an empty body.
2. Creata records in the OMOP database for each compostion using EOS's '/ehr' endpoint. (Make sure EOS has the relevant OMOCL mappings for the relevant archetypes).

## Mappings

We currently support CDM v5.4, if more tooling is provided v6 will be added in the future.

### configured Mappings

See [here](https://github.com/SevKohler/OMOCL)

### Usable with declarative mappings

| CDM table            | Supported               |
|----------------------|-------------------------|
| OBSERVATION_PERIOD   | automatically generated |
| VISIT_OCCURRENCE     | from AQL / automatically generated |
| VISIT_DETAIL         |                         |
| CONDITION_OCCURRENCE | x                       | 
| DRUG_EXPOSURE        | x                       |
| PROCEDURE_OCCURRENCE | x                       |
| DEVICE_EXPOSURE      | x                       |
| MEASUREMENT          | x                       |
| OBSERVATION          | x                       |
| DEATH                | x                       | 
| NOTE                 |                         | 
| NOTE_NLP             |                         | 
| SPECIMEN             | x                       |
| FACT_RELATIONSHIP    |                         |
| DRUG_ERA             | automatically generated |
| CONDITION_ERA        | automatically generated |

### Visit generation:
Visits can now be generated from defined AQL and calling to the /visit endpoint. AQL must contain four fields: path to ehr_id, path to source visit identifier, path to the start date, and path to the end date. The process executes the query and groups them by ehr_id+source_id, calculating min(start date) and max(end date) for each one of them. Null end dates are interpreted as end date=start date. As an example see the following sample query:

```sql
SELECT e/ehr_id/value as ehr_id,
       c/context/other_context/items[openEHR-EHR-CLUSTER.case_identification.v0]/items[at0001]/value/value as source_id,
       c/content[openEHR-EHR-ADMIN_ENTRY.hospitalization.v0]/data[at0001]/items[at0004]/value/value as begin,
       c/content[openEHR-EHR-ADMIN_ENTRY.hospitalization.v0]/data[at0001]/items[at0005]/value/value as end
FROM EHR e
CONTAINS COMPOSITION c
WHERE c/archetype_details/template_id/value='Patientenaufenthalt'

```
This query usually points to the context of the Composition, but is not limited to that.
After generating visits, whenever a new Composition is procesed by EOS, it will look (by using the templateid and visitsource configuration parameters) for existing visits generated for that patient, and if they exist, it will use them as the corresponding visit for each one of the selected clinical tables. If no AQL is selected then automatic visits are created from the clinical entity dates (e.g. the diagnosis or the measurement date).
The following is a snippet of the configuration in the application.yml containing the visit configuration

```yml
eos:
  person-conversion:
    mode: automatic #conversion for composition conversion
  visit-conversion:
    aql: SELECT e/ehr_id/value as ehr_id, c/context/other_context/items[openEHR-EHR-CLUSTER.case_identification.v0]/items[at0001]/value/value as source_id, c/content[openEHR-EHR-ADMIN_ENTRY.hospitalization.v0]/data[at0001]/items[at0004]/value/value as begin, c/content[openEHR-EHR-ADMIN_ENTRY.hospitalization.v0]/data[at0001]/items[at0005]/value/value as end FROM EHR e CONTAINS COMPOSITION c WHERE c/archetype_details/template_id/value='Patientenaufenthalt'
    templateid: Patientenaufenthalt
    visitsource: /context/other_context/items[openEHR-EHR-CLUSTER.case_identification.v0]/items[at0001]/value/value
...
```

### DISCLAIMER:

- INTERVAL_EVENT is currently only mapping the event time
- Interval of Quantity is not supported
- Multiplication is currently only supported for DrugExposure quantity, will be added to other configs on demand.
- Dv_Proportion only type 2 is supported for unit due to lack of sample data. If samples are provided this can be added.
- CustomConverters are not supported for Person Conversions
- range low range high and operator will map normal range and magnitude status if DV Quantity is provided and NOT
  magnitude.

Special thanks
---
We thank the Georgia Tech Research Institute for providing jpa classes for OMOP and vita systems for their free
accessible openEHR tooling.


