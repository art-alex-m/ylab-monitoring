package io.ylab.monitoring.db.jdbc.repository;

import io.ylab.monitoring.db.jdbc.exeption.JdbcDbException;
import io.ylab.monitoring.domain.core.model.DomainUser;
import io.ylab.monitoring.domain.core.model.MeterReading;
import io.ylab.monitoring.domain.core.repository.GetActualMeterReadingsInputDbRepository;
import io.ylab.monitoring.domain.core.repository.GetMonthMeterReadingsInputDbRepository;
import io.ylab.monitoring.domain.core.repository.ViewMeterReadingsHistoryInputDbRepository;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@AllArgsConstructor
public class JdbcAdminMeterReadingsDbRepository extends AbstractMeterReadingsDbRepository
        implements GetActualMeterReadingsInputDbRepository, GetMonthMeterReadingsInputDbRepository,
        ViewMeterReadingsHistoryInputDbRepository {

    private final static String SQL_FIND_ACTUAL = "db/sql/meter-readings-admin-find-actual.sql";
    private final static String SQL_FIND_ALL = "db/sql/meter-readings-admin-find-all.sql";
    private final static String SQL_FIND_BY_PERIOD = "db/sql/meter-readings-admin-find-by-period.sql";

    private final SqlQueryRepository queryRepository;

    private Connection connection;

    @Override
    public List<MeterReading> findActualByUser(DomainUser user) {
        try {
            PreparedStatement statement = connection.prepareStatement(queryRepository.getSql(SQL_FIND_ACTUAL));
            return getMeterReadings(statement);
        } catch (SQLException ex) {
            throw new JdbcDbException(ex);
        }
    }

    @Override
    public List<MeterReading> findByUserAndPeriod(DomainUser user, Instant period) {
        try {
            PreparedStatement statement = connection.prepareStatement(queryRepository.getSql(SQL_FIND_BY_PERIOD));
            statement.setTimestamp(1, Timestamp.from(period));
            return getMeterReadings(statement);
        } catch (SQLException ex) {
            throw new JdbcDbException(ex);
        }
    }

    @Override
    public List<MeterReading> findByUser(DomainUser user) {
        try {
            PreparedStatement statement = connection.prepareStatement(queryRepository.getSql(SQL_FIND_ALL));
            return getMeterReadings(statement);
        } catch (SQLException ex) {
            throw new JdbcDbException(ex);
        }
    }
}
