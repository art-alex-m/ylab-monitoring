package io.ylab.monitoring.db.jdbc.repository;

import io.ylab.monitoring.core.model.CoreDomainUser;
import io.ylab.monitoring.core.model.CoreMeter;
import io.ylab.monitoring.db.jdbc.model.JdbcMeterReading;
import io.ylab.monitoring.db.jdbc.provider.SqlConnectionProvider;
import io.ylab.monitoring.domain.core.model.MeterReading;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * Базовые алгоритмы репозиториев работы с показаниями счетчиков
 */
public abstract class AbstractMeterReadingsDbRepository extends AbstractDbRepository {

    public AbstractMeterReadingsDbRepository(SqlQueryRepository queryRepository,
            SqlConnectionProvider connectionProvider) {
        super(queryRepository, connectionProvider);
    }

    public AbstractMeterReadingsDbRepository(SqlQueryRepository queryRepository, Connection connection) {
        super(queryRepository, connection);
    }

    protected List<MeterReading> getMeterReadings(PreparedStatement statement) throws SQLException {
        try (ResultSet records = statement.executeQuery()) {
            if (!records.isBeforeFirst()) {
                return Collections.emptyList();
            }
            List<MeterReading> itemList = new LinkedList<>();
            while (records.next()) {
                itemList.add(createModel(records));
            }
            return itemList;
        }
    }

    protected MeterReading createModel(ResultSet records) throws SQLException {
        return JdbcMeterReading.builder()
                .value(records.getLong(JdbcMeterReading.VALUE))
                .user(new CoreDomainUser(UUID.fromString(records.getString(JdbcMeterReading.USER))))
                .period(records.getTimestamp(JdbcMeterReading.PERIOD).toInstant())
                .createdAt(records.getTimestamp(JdbcMeterReading.CREATED_AT).toInstant())
                .id(UUID.fromString(records.getString(JdbcMeterReading.ID)))
                .meter(new CoreMeter(
                        UUID.fromString(records.getString(JdbcMeterReading.METER_ID)),
                        records.getString(JdbcMeterReading.METER_NAME)))
                .build();
    }
}
