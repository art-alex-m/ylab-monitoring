package io.ylab.monitoring.db.jdbc.repository;

import io.ylab.monitoring.db.jdbc.exeption.JdbcDbException;
import io.ylab.monitoring.db.jdbc.model.JdbcAuthUser;
import io.ylab.monitoring.domain.auth.model.AuthUser;
import io.ylab.monitoring.domain.auth.repository.UserLoginInputDbRepository;
import io.ylab.monitoring.domain.auth.repository.UserRegistrationInputDbRepository;
import io.ylab.monitoring.domain.core.model.DomainRole;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
public class JdbcAuthUserDbRepository implements UserLoginInputDbRepository, UserRegistrationInputDbRepository {

    private Connection connection;

    @Override
    public Optional<AuthUser> findByUsername(String username) {
        String sql = "select uuid, username, password, role from auth_users where username = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
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
        } catch (SQLException ex) {
            throw new JdbcDbException(ex);
        }

        return Optional.empty();
    }

    @Override
    public boolean create(AuthUser user) {
        String sql = "insert into auth_users (uuid, username, password, role) values (?, ?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, user.getId().toString());
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getRole().name());
            return statement.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new JdbcDbException(ex);
        }
    }

    @Override
    public boolean existsByUsername(String username) {
        String sql = "select 1 from auth_users where username = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.isBeforeFirst();
            }
        } catch (SQLException ex) {
            throw new JdbcDbException(ex);
        }
    }
}
