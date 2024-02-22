package io.ylab.monitoring.db.jdbc.repository;

import io.ylab.monitoring.core.model.CoreMeter;
import io.ylab.monitoring.db.jdbc.exeption.JdbcDbException;
import io.ylab.monitoring.db.jdbc.provider.SqlConnection;
import io.ylab.monitoring.db.jdbc.provider.SqlConnectionProvider;
import io.ylab.monitoring.domain.core.model.Meter;
import io.ylab.monitoring.domain.core.repository.SubmissionMeterReadingsInputMeterDbRepository;
import io.ylab.monitoring.domain.core.repository.ViewMetersInputDbRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class JdbcUserMetersDbRepository extends AbstractDbRepository
        implements SubmissionMeterReadingsInputMeterDbRepository, ViewMetersInputDbRepository {

    private final static String SQL_CREATE = "db/sql/meters-create.sql";
    private final static String SQL_FIND_ALL = "db/sql/meters-find-all.sql";
    private final static String SQL_FIND_BY_NAME = "db/sql/meters-find-by-name.sql";

    public JdbcUserMetersDbRepository(SqlQueryRepository queryRepository, SqlConnectionProvider connectionProvider) {
        super(queryRepository, connectionProvider);
    }

    public JdbcUserMetersDbRepository(SqlQueryRepository queryRepository, Connection connection) {
        super(queryRepository, connection);
    }


    @Override
    public Optional<Meter> findByName(String meterName) {
        try (SqlConnection connection = getConnection();
             PreparedStatement preparedStatement = connection.get()
                     .prepareStatement(queryRepository.getSql(SQL_FIND_BY_NAME))) {
            preparedStatement.setString(1, meterName);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next()
                        ? Optional.of(createModel(resultSet))
                        : Optional.empty();
            }
        } catch (Exception ex) {
            throw new JdbcDbException(ex);
        }
    }

    @Override
    public List<Meter> findAll() {
        try (SqlConnection connection = getConnection();
             Statement statement = connection.get().createStatement()) {
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
        } catch (Exception ex) {
            throw new JdbcDbException(ex);
        }
    }

    public boolean store(Meter meter) {
        try (SqlConnection connection = getConnection();
             PreparedStatement statement = connection.get().prepareStatement(queryRepository.getSql(SQL_CREATE))) {
            statement.setString(1, meter.getId().toString());
            statement.setString(2, meter.getName());
            return statement.executeUpdate() > 0;
        } catch (Exception ex) {
            throw new JdbcDbException(ex);
        }
    }

    private Meter createModel(ResultSet resultSet) throws SQLException {
        return new CoreMeter(
                UUID.fromString(resultSet.getString("uuid")),
                resultSet.getString("name"));
    }
}
