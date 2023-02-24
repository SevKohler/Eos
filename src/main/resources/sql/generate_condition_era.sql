USE @TARGET_CDMV5;

truncate table @TARGET_CDMV5_SCHEMA.condition_era;

/****
CONDITION ERA
Note: Eras derived from CONDITION_OCCURRENCE table, using 30d gap
 ****/
IF OBJECT_ID('tempdb..#condition_era_phase_1', 'U') IS NOT NULL
DROP TABLE #condition_era_phase_1;

/* / */

IF OBJECT_ID('tempdb..#cteConditionTarget', 'U') IS NOT NULL
DROP TABLE #cteConditionTarget;

/* / */

-- create base eras from the concepts found in condition_occurrence
SELECT co.PERSON_ID
     ,co.condition_concept_id
     ,co.CONDITION_START_DATE
     ,COALESCE(co.CONDITION_END_DATE, DATEADD(day, 1, CONDITION_START_DATE)) AS CONDITION_END_DATE
INTO #cteConditionTarget
FROM @TARGET_CDMV5_SCHEMA.CONDITION_OCCURRENCE co;

/* / */

IF OBJECT_ID('tempdb..#cteCondEndDates', 'U') IS NOT NULL
DROP TABLE #cteCondEndDates;

/* / */

SELECT PERSON_ID
     ,CONDITION_CONCEPT_ID
     ,DATEADD(day, - 30, EVENT_DATE) AS END_DATE -- unpad the end date
INTO #cteCondEndDates
FROM (
         SELECT E1.PERSON_ID
              ,E1.CONDITION_CONCEPT_ID
              ,E1.EVENT_DATE
              ,COALESCE(E1.START_ORDINAL, MAX(E2.START_ORDINAL)) START_ORDINAL
              ,E1.OVERALL_ORD
         FROM (
                  SELECT PERSON_ID
                       ,CONDITION_CONCEPT_ID
                       ,EVENT_DATE
                       ,EVENT_TYPE
                       ,START_ORDINAL
                       ,ROW_NUMBER() OVER (
                PARTITION BY PERSON_ID
                ,CONDITION_CONCEPT_ID ORDER BY EVENT_DATE
                    ,EVENT_TYPE
                ) AS OVERALL_ORD -- this re-numbers the inner UNION so all rows are numbered ordered by the event date
                  FROM (
                           -- select the start dates, assigning a row number to each
                           SELECT PERSON_ID
                                ,CONDITION_CONCEPT_ID
                                ,CONDITION_START_DATE AS EVENT_DATE
                                ,- 1 AS EVENT_TYPE
                                ,ROW_NUMBER() OVER (
                    PARTITION BY PERSON_ID
                    ,CONDITION_CONCEPT_ID ORDER BY CONDITION_START_DATE
                    ) AS START_ORDINAL
                           FROM #cteConditionTarget

                           UNION ALL

                           -- pad the end dates by 30 to allow a grace period for overlapping ranges.
                           SELECT PERSON_ID
                                ,CONDITION_CONCEPT_ID
                                ,DATEADD(day, 30, CONDITION_END_DATE)
                                ,1 AS EVENT_TYPE
                                ,NULL
                           FROM #cteConditionTarget
                       ) RAWDATA
              ) E1
                  INNER JOIN (
             SELECT PERSON_ID
                  ,CONDITION_CONCEPT_ID
                  ,CONDITION_START_DATE AS EVENT_DATE
                  ,ROW_NUMBER() OVER (
                PARTITION BY PERSON_ID
                ,CONDITION_CONCEPT_ID ORDER BY CONDITION_START_DATE
                ) AS START_ORDINAL
             FROM #cteConditionTarget
         ) E2 ON E1.PERSON_ID = E2.PERSON_ID
             AND E1.CONDITION_CONCEPT_ID = E2.CONDITION_CONCEPT_ID
             AND E2.EVENT_DATE <= E1.EVENT_DATE
         GROUP BY E1.PERSON_ID
                ,E1.CONDITION_CONCEPT_ID
                ,E1.EVENT_DATE
                ,E1.START_ORDINAL
                ,E1.OVERALL_ORD
     ) E
WHERE (2 * E.START_ORDINAL) - E.OVERALL_ORD = 0;

/* / */

IF OBJECT_ID('tempdb..#cteConditionEnds', 'U') IS NOT NULL
DROP TABLE #cteConditionEnds;

/* / */

SELECT c.PERSON_ID
     ,c.CONDITION_CONCEPT_ID
     ,c.CONDITION_START_DATE
     ,MIN(e.END_DATE) AS ERA_END_DATE
INTO #cteConditionEnds
FROM #cteConditionTarget c
         INNER JOIN #cteCondEndDates e ON c.PERSON_ID = e.PERSON_ID
    AND c.CONDITION_CONCEPT_ID = e.CONDITION_CONCEPT_ID
    AND e.END_DATE >= c.CONDITION_START_DATE
GROUP BY c.PERSON_ID
       ,c.CONDITION_CONCEPT_ID
       ,c.CONDITION_START_DATE;

/* / */

INSERT INTO @TARGET_CDMV5_SCHEMA.condition_era (
                                                condition_era_id
                                               ,person_id
                                               ,condition_concept_id
                                               ,condition_era_start_date
                                               ,condition_era_end_date
                                               ,condition_occurrence_count
)
SELECT row_number() OVER (
        ORDER BY person_id
        ) AS condition_era_id
    ,person_id
     ,CONDITION_CONCEPT_ID
     ,min(CONDITION_START_DATE) AS CONDITION_ERA_START_DATE
     ,ERA_END_DATE AS CONDITION_ERA_END_DATE
     ,COUNT(*) AS CONDITION_OCCURRENCE_COUNT
FROM #cteConditionEnds
GROUP BY person_id
       ,CONDITION_CONCEPT_ID
       ,ERA_END_DATE;

