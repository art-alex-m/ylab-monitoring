package io.ylab.monitoring.app.console;

import io.ylab.monitoring.app.console.config.*;
import io.ylab.monitoring.app.console.event.AppMonitoringEventPublisher;
import io.ylab.monitoring.core.service.CorePeriodService;
import io.ylab.monitoring.domain.core.event.MonitoringEventPublisher;
import io.ylab.monitoring.domain.core.model.DomainRole;
import io.ylab.monitoring.domain.core.model.Meter;
import io.ylab.monitoring.domain.core.service.PeriodService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        System.out.println("it works!");

        List<Meter> meterList = List.of();
        DatabaseConfig databaseConfig = new AppMemoDbConfig().setMeters(meterList);
        MonitoringEventPublisher eventPublisher = new AppMonitoringEventPublisher();
        PeriodService periodService = new CorePeriodService();
        AppInputResponseFactoryConfig responseFactoryConfig = new AppInputResponseFactoryConfig();

        Map<DomainRole, AbstractInteractorConfig> interactorConfigMap = new HashMap<>();
        interactorConfigMap.put(DomainRole.ANONYMOUS, AppAnonymousInteractorConfig.builder()
                    .databaseConfig(databaseConfig)
                    .responseFactoryConfig(responseFactoryConfig)
                    .eventPublisher(eventPublisher)
                    .build());

        interactorConfigMap.put(DomainRole.USER, AppUserInteractorConfig.builder()
                    .databaseConfig(databaseConfig)
                    .responseFactoryConfig(responseFactoryConfig)
                    .eventPublisher(eventPublisher)
                    .periodService(periodService)
                    .build());

        interactorConfigMap.put(DomainRole.ADMIN, AppAdminInteractorConfig.builder()
                    .databaseConfig(databaseConfig)
                    .responseFactoryConfig(responseFactoryConfig)
                    .eventPublisher(eventPublisher)
                    .periodService(periodService)
                    .build());
    }
}
