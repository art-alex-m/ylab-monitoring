package io.ylab.monitoring.db.jdbc.repository;

import io.ylab.monitoring.core.model.CoreDomainUser;
import io.ylab.monitoring.db.jdbc.exeption.JdbcDbException;
import io.ylab.monitoring.db.jdbc.model.JdbcAuditItem;
import io.ylab.monitoring.db.jdbc.provider.SqlConnection;
import io.ylab.monitoring.db.jdbc.provider.SqlConnectionProvider;
import io.ylab.monitoring.domain.audit.model.AuditItem;
import io.ylab.monitoring.domain.audit.repository.CreateAuditLogInputDbRepository;
import io.ylab.monitoring.domain.audit.repository.ViewAuditLogInputDbRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class JdbcAuditLogDbRepository extends AbstractDbRepository
        implements CreateAuditLogInputDbRepository, ViewAuditLogInputDbRepository {

    private final static String SQL_CREATE = "db/sql/audit-items-create.sql";
    private final static String SQL_FIND_ALL = "db/sql/audit-items-find-all.sql";

    public JdbcAuditLogDbRepository(SqlQueryRepository queryRepository, SqlConnectionProvider connectionProvider) {
        super(queryRepository, connectionProvider);
    }

    public JdbcAuditLogDbRepository(SqlQueryRepository queryRepository, Connection connection) {
        super(queryRepository, connection);
    }

    @Override
    public boolean create(AuditItem auditItem) {
        try (SqlConnection connection = getConnection();
             PreparedStatement statement = connection.get().prepareStatement(queryRepository.getSql(SQL_CREATE))) {
            statement.setString(1, auditItem.getUser().getId().toString());
            statement.setString(2, auditItem.getName());
            statement.setTimestamp(3, Timestamp.from(auditItem.getOccurredAt()));
            return statement.executeUpdate() > 0;
        } catch (Exception ex) {
            throw new JdbcDbException(ex);
        }
    }

    @Override
    public List<AuditItem> findAll() {
        try (SqlConnection connection = getConnection();
             Statement statement = connection.get().createStatement()) {
            try (ResultSet records = statement.executeQuery(queryRepository.getSql(SQL_FIND_ALL))) {
                if (!records.isBeforeFirst()) {
                    return Collections.emptyList();
                }
                List<AuditItem> itemList = new LinkedList<>();
                while (records.next()) {
                    itemList.add(createModel(records));
                }
                return itemList;
            }

        } catch (Exception ex) {
            throw new JdbcDbException(ex);
        }
    }

    private AuditItem createModel(ResultSet records) throws SQLException {
        return JdbcAuditItem.builder()
                .name(records.getString(JdbcAuditItem.NAME))
                .occurredAt(records.getTimestamp(JdbcAuditItem.OCCURRED_AT).toInstant())
                .user(new CoreDomainUser(UUID.fromString(records.getString(JdbcAuditItem.USER))))
                .build();
    }
}
