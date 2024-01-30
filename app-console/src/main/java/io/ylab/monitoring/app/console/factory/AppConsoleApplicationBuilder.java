package io.ylab.monitoring.app.console.factory;

import io.ylab.monitoring.app.console.config.*;
import io.ylab.monitoring.app.console.event.AppCreateAuditLogEventHandler;
import io.ylab.monitoring.app.console.event.AppMonitoringEventPublisher;
import io.ylab.monitoring.app.console.model.*;
import io.ylab.monitoring.app.console.service.AppCommandParser;
import io.ylab.monitoring.audit.boundary.AuditCreateAuditLogInteractor;
import io.ylab.monitoring.auth.model.AuthAuthUser;
import io.ylab.monitoring.auth.service.AuthPasswordEncoder;
import io.ylab.monitoring.core.model.CoreMeter;
import io.ylab.monitoring.core.service.CorePeriodService;
import io.ylab.monitoring.domain.auth.event.UserLogined;
import io.ylab.monitoring.domain.auth.event.UserLogouted;
import io.ylab.monitoring.domain.auth.model.AuthUser;
import io.ylab.monitoring.domain.auth.service.PasswordEncoder;
import io.ylab.monitoring.domain.core.event.MonitoringEvent;
import io.ylab.monitoring.domain.core.model.DomainRole;
import io.ylab.monitoring.domain.core.model.Meter;
import io.ylab.monitoring.domain.core.service.PeriodService;

import java.io.PrintStream;
import java.util.*;

/**
 * Конфигуратор контекста консольного приложения
 */
public class AppConsoleApplicationBuilder {
    private final static String ADMIN_DEFAULT_LOGIN = "admin";

    private final static String ADMIN_DEFAULT_PASSWORD = "admin";

    private final static String DEFAULT_PASSWORD_SALT = "gahhc5X5bYPIbsKSQw";

    private final Map<DomainRole, CommandExecutorChain> roleExecutors = new HashMap<>();

    private AppCommandParser commandParser = new AppCommandParser();

    private AppUserContext userContext = new AppUserContext();

    private PrintStream printStream = System.out;

    private DatabaseConfig databaseConfig;

    private AppMonitoringEventPublisher eventPublisher = new AppMonitoringEventPublisher();

    private PeriodService periodService = new CorePeriodService();

    private AppInputResponseFactoryConfig responseFactoryConfig = new AppInputResponseFactoryConfig();

    private Map<DomainRole, AbstractInteractorConfig> interactorConfigMap = new HashMap<>();

    private AppCommandExecutorBuilderFactory executorBuilderFactory = new AppCommandExecutorBuilderFactory();

    private PasswordEncoder passwordEncoder = new AuthPasswordEncoder().setSalt(DEFAULT_PASSWORD_SALT);

    private List<Meter> meterList = new LinkedList<>();

    private AuthUser adminUser;

    /**
     * Добавляет новый тип показания счетчика в базу
     *
     * @param meterName строка
     * @return AppConsoleApplicationBuilder
     */
    public AppConsoleApplicationBuilder withMeter(String meterName) {
        meterList.add(new CoreMeter(UUID.randomUUID(), meterName));
        return this;
    }

    /**
     * Устанавливает логи и пароль пользователя с ролю администратора
     *
     * @param username имя пользователя
     * @param password пароль
     * @return AppConsoleApplicationBuilder
     */
    public AppConsoleApplicationBuilder withAdmin(String username, String password) {
        adminUser = AuthAuthUser.builder()
                .role(DomainRole.ADMIN)
                .username(username)
                .password(passwordEncoder.encode(password))
                .build();
        return this;
    }

    /**
     * Устанавливает конфигурацию базы данных приложения
     *
     * @param config DatabaseConfig
     * @return AppConsoleApplicationBuilder
     */
    public AppConsoleApplicationBuilder withDatabaseConfig(DatabaseConfig config) {
        databaseConfig = config;
        return this;
    }

    /**
     * Устанавливает логин и пароль администратора по-умолчанию "admin/admin"
     *
     * @return AppConsoleApplicationBuilder
     */
    public AppConsoleApplicationBuilder withDefaultAdmin() {
        return withAdmin(ADMIN_DEFAULT_LOGIN, ADMIN_DEFAULT_PASSWORD);
    }

    public AppConsoleApplication build() {
        if (databaseConfig == null) {
            databaseConfig = new AppMemoDbConfig();
        }

        databaseConfig.setMeters(meterList);

        if (adminUser != null) {
            databaseConfig.getUserRegistrationInputDbRepository().create(adminUser);
        }

        userContext.setAnonymous();

        initAdminInteractors();
        initUserInteractors();
        initAnonymousInteractors();
        initCommandChains();
        initEventListeners();

        return new AppConsoleApplication(commandParser, userContext, roleExecutors);
    }

    private void initEventListeners() {
        eventPublisher
                .subscribe(event -> userContext.setUser((AuthUser) event.getUser()), UserLogined.class)
                .subscribe(event -> userContext.setAnonymous(), UserLogouted.class)
                .subscribe(createAuditLogEventHandler(), MonitoringEvent.class);
    }

    /**
     * Генерирует цепочки команд для разных ролей
     */
    private void initCommandChains() {
        for (DomainRole role : DomainRole.values()) {
            CommandExecutorChain head = executorBuilderFactory
                    .getBuilder(AppCommandName.HELP).build(userContext, null, printStream);

            CommandExecutorChain current = head;
            for (AbstractInteractorConfig.Entry entry : interactorConfigMap.get(role).getInteractors()) {
                current = current.setNext(executorBuilderFactory.getBuilder(entry.getName())
                        .build(userContext, entry.getInteractor(), printStream));
            }

            current.setNext(executorBuilderFactory
                    .getBuilder(AppCommandName.UNKNOWN).build(userContext, null, printStream));

            roleExecutors.put(role, head);
        }
    }

    /**
     * Генерирует сценарии ядра для пользователя
     */
    private void initUserInteractors() {
        interactorConfigMap.put(DomainRole.USER, AppUserInteractorConfig.builder()
                .databaseConfig(databaseConfig)
                .responseFactoryConfig(responseFactoryConfig)
                .eventPublisher(eventPublisher)
                .periodService(periodService)
                .build());
    }

    /**
     * Генерирует сценарии ядра для анонима
     */
    private void initAnonymousInteractors() {
        interactorConfigMap.put(DomainRole.ANONYMOUS, AppAnonymousInteractorConfig.builder()
                .databaseConfig(databaseConfig)
                .eventPublisher(eventPublisher)
                .passwordEncoder(passwordEncoder)
                .build());
    }

    /**
     * Генерирует сценарии ядра для администратора
     */
    private void initAdminInteractors() {
        interactorConfigMap.put(DomainRole.ADMIN, AppAdminInteractorConfig.builder()
                .databaseConfig(databaseConfig)
                .responseFactoryConfig(responseFactoryConfig)
                .eventPublisher(eventPublisher)
                .periodService(periodService)
                .build());
    }

    /**
     * Инициирует обработчик событий для ведения лога аудита
     *
     * @return объект
     */
    private AppCreateAuditLogEventHandler createAuditLogEventHandler() {
        return new AppCreateAuditLogEventHandler(new AuditCreateAuditLogInteractor(
                databaseConfig.getCreateAuditLogInputDbRepository()));
    }
}
