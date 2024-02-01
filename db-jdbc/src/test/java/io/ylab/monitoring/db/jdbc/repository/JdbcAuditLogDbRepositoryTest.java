package io.ylab.monitoring.db.jdbc.repository;

import io.ylab.monitoring.db.jdbc.model.JdbcAuditItem;
import io.ylab.monitoring.db.jdbc.service.JdbcTestHelperFactory;
import io.ylab.monitoring.db.jdbc.service.TestConnection;
import io.ylab.monitoring.db.jdbc.service.TestDatabaseExtension;
import io.ylab.monitoring.domain.audit.model.AuditItem;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.Instant;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(TestDatabaseExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class JdbcAuditLogDbRepositoryTest {

    private final static JdbcTestHelperFactory testFactory = new JdbcTestHelperFactory();

    private final SqlQueryRepository queryRepository = new SqlQueryResourcesRepository();

    @TestConnection
    private Connection connection;

    private JdbcAuditLogDbRepository sut;

    @BeforeEach
    void setUp() {
        sut = new JdbcAuditLogDbRepository(queryRepository, connection);
    }

    @Test
    @Order(100)
    void givenAuditItem_whenCreate_thenSuccess() {
        AuditItem auditItem = JdbcAuditItem.builder()
                .name("test")
                .occurredAt(Instant.now())
                .user(testFactory.testUserA)
                .build();

        boolean result = sut.create(auditItem);

        assertThat(result).isTrue();
    }

    @Test
    @Order(1)
    void givenMethodCall_whenFindAll_thenSuccess() {
        List<AuditItem> result = sut.findAll();

        assertThat(result).isNotEmpty().hasSize(4);
        assertThat(result.get(0).getName()).isEqualTo("4");
    }

    @Test
    @Order(1000)
    void givenEmptyDb_whenFindAll_thenSuccess() throws SQLException {
        connection.createStatement().execute("delete from audit_items");

        List<AuditItem> result = sut.findAll();

        assertThat(result).isEmpty();
        assertThat(result).isEqualTo(Collections.emptyList());
    }
}