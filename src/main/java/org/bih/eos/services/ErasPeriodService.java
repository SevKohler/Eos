package org.bih.eos.services;

import org.apache.commons.io.FileUtils;
import org.bih.eos.fileloader.FileLoader;
import org.ohdsi.sql.SqlTranslate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ErasPeriodService {

    @Value("${omop-bridge.cdm.database-name}")
    private String databaseName;
    @Value("${omop-bridge.cdm.schema}")
    private String databaseSchema;
    @PersistenceUnit
    EntityManagerFactory entityManagerFactory;

    public void executeSqls() {
        List<String> sqlScripts = getSqlScripts();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        for (String sql : sqlScripts) {
            entityManager.createNativeQuery(sql).executeUpdate();
        }
        entityManager.getTransaction().commit();
    }

    private List<String> getSqlScripts() {
        List<File> fileList = FileLoader.loadFiles("sql/", "Error loading sql renderer files for Condition Era and Drug Era");
        List<String> sql = new ArrayList<>();
        for (File file : fileList) {
            try {
                String sqlFile = FileUtils.readFileToString(file, "UTF-8");
                sqlFile = sqlFile.replace("@TARGET_CDMV5_SCHEMA", databaseSchema);
                sqlFile = sqlFile.replace("@TARGET_CDMV5", databaseName);
                sqlFile = sqlFile.replace("@cdm_schema", databaseSchema);
                sql.add(SqlTranslate.translateSql(sqlFile, "postgresql"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sql;
    }
}
