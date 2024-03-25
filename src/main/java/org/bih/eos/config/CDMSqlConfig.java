package org.bih.eos.config;

import org.bih.eos.services.ErasPeriodService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;


@Configuration
@EnableScheduling
@ConditionalOnProperty(prefix = "eos.eras-period-cron", name = "enabled", havingValue = "true", matchIfMissing = true)
public class CDMSqlConfig {
    final ErasPeriodService executeSqlService;

    public CDMSqlConfig(ErasPeriodService executeSqlService) {
        this.executeSqlService = executeSqlService;
    }

    @Scheduled(cron = "${eos.eras-period-cron.cron}")
    @Transactional
    void executeSqls() {
        executeSqlService.executeSqls();
    }
}
