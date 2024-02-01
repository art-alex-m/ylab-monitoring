package io.ylab.monitoring.db.jdbc.repository;

import io.ylab.monitoring.core.model.CoreMeter;
import io.ylab.monitoring.db.jdbc.exeption.JdbcDbException;
import io.ylab.monitoring.domain.core.model.Meter;
import io.ylab.monitoring.domain.core.repository.SubmissionMeterReadingsInputMeterDbRepository;
import io.ylab.monitoring.domain.core.repository.ViewMetersInputDbRepository;
import lombok.AllArgsConstructor;

import java.sql.*;
import java.util.*;

@AllArgsConstructor
public class JdbcUserMetersDbRepository implements SubmissionMeterReadingsInputMeterDbRepository,
        ViewMetersInputDbRepository {

    private final static String SQL_CREATE = "db/sql/meters-create.sql";
    private final static String SQL_FIND_ALL = "db/sql/meters-find-all.sql";
    private final static String SQL_FIND_BY_NAME = "db/sql/meters-find-by-name.sql";

    private final SqlQueryRepository queryRepository;

    private Connection connection;

    @Override
    public Optional<Meter> findByName(String meterName) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(queryRepository.getSql(SQL_FIND_BY_NAME));
            preparedStatement.setString(1, meterName);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next()
                        ? Optional.of(createModel(resultSet))
                        : Optional.empty();
            }
        } catch (SQLException ex) {
            throw new JdbcDbException(ex);
        }
    }

    @Override
    public List<Meter> findAll() {
        try {
            Statement statement = connection.createStatement();
            try (ResultSet records = statement.executeQuery(queryRepository.getSql(SQL_FIND_ALL))) {
                if (!records.isBeforeFirst()) {
                    return Collections.emptyList();
                }
                List<Meter> itemList = new LinkedList<>();
                while (records.next()) {
                    itemList.add(createModel(records));
                }
                return itemList;
            }
        } catch (SQLException ex) {
            throw new JdbcDbException(ex);
        }
    }

    public boolean store(Meter meter) {
        try {
            PreparedStatement statement = connection.prepareStatement(queryRepository.getSql(SQL_CREATE));
            statement.setString(1, meter.getId().toString());
            statement.setString(2, meter.getName());
            return statement.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new JdbcDbException(ex);
        }
    }

    private Meter createModel(ResultSet resultSet) throws SQLException {
        return new CoreMeter(
                UUID.fromString(resultSet.getString("uuid")),
                resultSet.getString("name"));
    }
}
