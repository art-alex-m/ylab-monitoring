package io.ylab.monitoring.db.jdbc.repository;

import io.ylab.monitoring.db.jdbc.exeption.JdbcDbException;
import io.ylab.monitoring.domain.core.model.DomainUser;
import io.ylab.monitoring.domain.core.model.Meter;
import io.ylab.monitoring.domain.core.model.MeterReading;
import io.ylab.monitoring.domain.core.repository.GetActualMeterReadingsInputDbRepository;
import io.ylab.monitoring.domain.core.repository.GetMonthMeterReadingsInputDbRepository;
import io.ylab.monitoring.domain.core.repository.SubmissionMeterReadingsInputDbRepository;
import io.ylab.monitoring.domain.core.repository.ViewMeterReadingsHistoryInputDbRepository;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@AllArgsConstructor
public class JdbcUserMeterReadingsDbRepository extends AbstractMeterReadingsDbRepository
        implements GetActualMeterReadingsInputDbRepository, GetMonthMeterReadingsInputDbRepository,
        SubmissionMeterReadingsInputDbRepository, ViewMeterReadingsHistoryInputDbRepository {

    private final static String SQL_FIND_ACTUAL_BY_USER = "db/sql/meter-readings-find-actual-by-user.sql";
    private final static String SQL_EXISTS_BY_USER_PERIOD_METER = "db/sql/meter-readings-exists-by-user-period-meter.sql";
    private final static String SQL_FIND_BY_USER = "db/sql/meter-readings-find-by-user.sql";
    private final static String SQL_FIND_BY_USER_PERIOD = "db/sql/meter-readings-find-by-user-period.sql";
    private final static String SQL_CREATE = "db/sql/meter-readings-create.sql";

    private final SqlQueryRepository queryRepository;

    private Connection connection;

    @Override
    public List<MeterReading> findActualByUser(DomainUser user) {
        try {
            PreparedStatement statement = connection.prepareStatement(queryRepository.getSql(SQL_FIND_ACTUAL_BY_USER));
            statement.setString(1, user.getId().toString());
            return getMeterReadings(statement);
        } catch (SQLException ex) {
            throw new JdbcDbException(ex);
        }
    }

    @Override
    public List<MeterReading> findByUserAndPeriod(DomainUser user, Instant period) {
        try {
            PreparedStatement statement = connection.prepareStatement(queryRepository.getSql(SQL_FIND_BY_USER_PERIOD));
            statement.setString(1, user.getId().toString());
            statement.setTimestamp(2, Timestamp.from(period));
            return getMeterReadings(statement);
        } catch (SQLException ex) {
            throw new JdbcDbException(ex);
        }
    }

    @Override
    public boolean save(MeterReading reading) {
        try {
            PreparedStatement statement = connection.prepareStatement(queryRepository.getSql(SQL_CREATE));
            statement.setString(1, reading.getId().toString());
            statement.setString(2, reading.getUser().getId().toString());
            statement.setString(3, reading.getMeter().getId().toString());
            statement.setTimestamp(4, Timestamp.from(reading.getPeriod()));
            statement.setLong(5, reading.getValue());
            statement.setTimestamp(6, Timestamp.from(reading.getCreatedAt()));
            return statement.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new JdbcDbException(ex);
        }
    }

    @Override
    public boolean existsByUserAndPeriodAndMeter(DomainUser user, Instant period, Meter meter) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    queryRepository.getSql(SQL_EXISTS_BY_USER_PERIOD_METER));
            statement.setString(1, user.getId().toString());
            statement.setTimestamp(2, Timestamp.from(period));
            statement.setString(3, meter.getId().toString());
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.isBeforeFirst();
            }
        } catch (SQLException ex) {
            throw new JdbcDbException(ex);
        }
    }

    @Override
    public List<MeterReading> findByUser(DomainUser user) {
        try {
            PreparedStatement statement = connection.prepareStatement(queryRepository.getSql(SQL_FIND_BY_USER));
            statement.setString(1, user.getId().toString());
            return getMeterReadings(statement);
        } catch (SQLException ex) {
            throw new JdbcDbException(ex);
        }
    }
}
