package io.ylab.monitoring.app.console.config;

import io.ylab.monitoring.app.console.model.DatabaseConfig;
import io.ylab.monitoring.db.memo.repository.*;
import io.ylab.monitoring.domain.audit.repository.CreateAuditLogInputDbRepository;
import io.ylab.monitoring.domain.audit.repository.ViewAuditLogInputDbRepository;
import io.ylab.monitoring.domain.auth.repository.UserLoginInputDbRepository;
import io.ylab.monitoring.domain.auth.repository.UserRegistrationInputDbRepository;
import io.ylab.monitoring.domain.core.model.Meter;
import io.ylab.monitoring.domain.core.repository.*;
import lombok.Getter;

import java.util.List;

/**
 * Конфигуратор хранилища в оперативной памяти
 */
@Getter
public class AppMemoDbConfig implements DatabaseConfig {

    private GetActualMeterReadingsInputDbRepository userActualMeterReadingsInputDbRepository;
    private GetMonthMeterReadingsInputDbRepository userMonthMeterReadingsInputDbRepository;
    private ViewMeterReadingsHistoryInputDbRepository userViewMeterReadingsHistoryInputDbRepository;
    private GetActualMeterReadingsInputDbRepository adminActualMeterReadingsInputDbRepository;
    private GetMonthMeterReadingsInputDbRepository adminMonthMeterReadingsInputDbRepository;
    private ViewMeterReadingsHistoryInputDbRepository adminViewMeterReadingsHistoryInputDbRepository;
    private SubmissionMeterReadingsInputMeterDbRepository submissionMeterReadingsInputMeterDbRepository;
    private SubmissionMeterReadingsInputDbRepository submissionMeterReadingsInputDbRepository;
    private ViewMetersInputDbRepository viewMetersInputDbRepository;
    private UserLoginInputDbRepository userLoginInputDbRepository;
    private UserRegistrationInputDbRepository userRegistrationInputDbRepository;
    private CreateAuditLogInputDbRepository createAuditLogInputDbRepository;
    private ViewAuditLogInputDbRepository viewAuditLogInputDbRepository;

    private MemoUserMetersDbRepository memoUserMetersDbRepository;


    public AppMemoDbConfig() {
        init();
    }

    /**
     * {@inheritDoc}
     */
    public DatabaseConfig setMeters(List<Meter> meterList) {
        meterList.forEach(m -> memoUserMetersDbRepository.store(m));
        return this;
    }

    private void init() {
        MemoMeterReadingsDatabase memoMeterReadingsDatabase = new MemoMeterReadingsDatabase();
        MemoAdminMeterReadingsDbRepository memoAdminMeterReadingsDbRepository =
                new MemoAdminMeterReadingsDbRepository(memoMeterReadingsDatabase);
        MemoUserMeterReadingsDbRepository memoUserMeterReadingsDbRepository =
                new MemoUserMeterReadingsDbRepository(memoMeterReadingsDatabase);
        MemoAuditLogDbRepository memoAuditLogDbRepository = new MemoAuditLogDbRepository();
        MemoAuthUserDbRepository memoAuthUserDbRepository = new MemoAuthUserDbRepository();
        memoUserMetersDbRepository = new MemoUserMetersDbRepository();

        userActualMeterReadingsInputDbRepository = memoUserMeterReadingsDbRepository;
        userMonthMeterReadingsInputDbRepository = memoUserMeterReadingsDbRepository;
        userViewMeterReadingsHistoryInputDbRepository = memoUserMeterReadingsDbRepository;
        adminActualMeterReadingsInputDbRepository = memoAdminMeterReadingsDbRepository;
        adminMonthMeterReadingsInputDbRepository = memoAdminMeterReadingsDbRepository;
        adminViewMeterReadingsHistoryInputDbRepository = memoAdminMeterReadingsDbRepository;
        submissionMeterReadingsInputDbRepository = memoUserMeterReadingsDbRepository;
        submissionMeterReadingsInputMeterDbRepository = memoUserMetersDbRepository;
        viewMetersInputDbRepository = memoUserMetersDbRepository;
        userLoginInputDbRepository = memoAuthUserDbRepository;
        userRegistrationInputDbRepository = memoAuthUserDbRepository;
        createAuditLogInputDbRepository = memoAuditLogDbRepository;
        viewAuditLogInputDbRepository = memoAuditLogDbRepository;
    }
}
