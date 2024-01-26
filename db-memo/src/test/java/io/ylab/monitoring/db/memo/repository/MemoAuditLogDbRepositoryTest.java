package io.ylab.monitoring.db.memo.repository;

import io.ylab.monitoring.core.model.CoreDomainUser;
import io.ylab.monitoring.domain.audit.model.AuditItem;
import io.ylab.monitoring.domain.core.model.DomainUser;
import lombok.Getter;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class MemoAuditLogDbRepositoryTest {

    @Test
    void create() {
        AuditItem item = new TestAuditItem("A");
        MemoAuditLogDbRepository sut = new MemoAuditLogDbRepository();

        boolean result = sut.create(item);

        assertThat(result).isTrue();
    }

    @Test
    void findAll() {
        List<String> auditNames = List.of("A", "B", "C");
        MemoAuditLogDbRepository sut = new MemoAuditLogDbRepository();
        auditNames.stream().map(TestAuditItem::new).forEach(sut::create);

        List<AuditItem> result = sut.findAll();

        assertThat(result)
                .hasSize(3)
                .map(AuditItem::getName).usingRecursiveComparison()
                .isNotEqualTo(auditNames);
        assertThat(result.get(0).getName()).isEqualTo("C");
    }

    @Getter
    static class TestAuditItem implements AuditItem {
        private Instant occurredAt = Instant.now();
        private DomainUser user = new CoreDomainUser(UUID.randomUUID());
        private String name;

        TestAuditItem(String name) {
            this.name = name;
        }
    }
}
