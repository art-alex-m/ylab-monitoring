package io.ylab.monitoring.db.jdbc.repository;

import io.ylab.monitoring.db.jdbc.exeption.JdbcDbException;
import io.ylab.monitoring.db.jdbc.model.JdbcAuthUser;
import io.ylab.monitoring.db.jdbc.provider.SqlConnection;
import io.ylab.monitoring.db.jdbc.provider.SqlConnectionProvider;
import io.ylab.monitoring.domain.auth.model.AuthUser;
import io.ylab.monitoring.domain.auth.repository.UserLoginInputDbRepository;
import io.ylab.monitoring.domain.auth.repository.UserRegistrationInputDbRepository;
import io.ylab.monitoring.domain.core.model.DomainRole;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.UUID;

public class JdbcAuthUserDbRepository extends AbstractDbRepository
        implements UserLoginInputDbRepository, UserRegistrationInputDbRepository {

    private static final String SQL_CREATE = "db/sql/auth-users-create.sql";
    private static final String SQL_FIND_BY_USERNAME = "db/sql/auth-users-find-by-username.sql";
    private static final String SQL_EXISTS_BY_USERNAME = "db/sql/auth-users-exists-by-username.sql";

    public JdbcAuthUserDbRepository(SqlQueryRepository queryRepository, SqlConnectionProvider connectionProvider) {
        super(queryRepository, connectionProvider);
    }

    public JdbcAuthUserDbRepository(SqlQueryRepository queryRepository, Connection connection) {
        super(queryRepository, connection);
    }

    @Override
    public Optional<AuthUser> findByUsername(String username) {
        try (SqlConnection connection = getConnection();
             PreparedStatement statement = connection.get().prepareStatement(
                     queryRepository.getSql(SQL_FIND_BY_USERNAME))) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(JdbcAuthUser.builder()
                            .id(UUID.fromString(resultSet.getString(JdbcAuthUser.ID)))
                            .password(resultSet.getString(JdbcAuthUser.PASSWORD))
                            .username(resultSet.getString(JdbcAuthUser.USERNAME))
                            .role(DomainRole.valueOf(resultSet.getString(JdbcAuthUser.ROLE))).build());
                }
            }
        } catch (Exception ex) {
            throw new JdbcDbException(ex);
        }

        return Optional.empty();
    }

    @Override
    public boolean create(AuthUser user) {
        try (SqlConnection connection = getConnection();
             PreparedStatement statement = connection.get().prepareStatement(queryRepository.getSql(SQL_CREATE))) {
            statement.setString(1, user.getId().toString());
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getRole().name());
            return statement.executeUpdate() > 0;
        } catch (Exception ex) {
            throw new JdbcDbException(ex);
        }
    }

    @Override
    public boolean existsByUsername(String username) {
        try (SqlConnection connection = getConnection();
             PreparedStatement statement = connection.get().prepareStatement(
                     queryRepository.getSql(SQL_EXISTS_BY_USERNAME))) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.isBeforeFirst();
            }
        } catch (Exception ex) {
            throw new JdbcDbException(ex);
        }
    }
}
