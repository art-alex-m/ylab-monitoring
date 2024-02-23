package io.ylab.monitoring.app.springboot.mapper;

import io.ylab.monitoring.app.springboot.out.AppAuditItem;
import io.ylab.monitoring.audit.model.AuditAuditItem;
import io.ylab.monitoring.audit.model.AuditDomainUser;
import io.ylab.monitoring.domain.audit.model.AuditItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class AuditItemAppAuditItemMapperImplTest {


    AuditItemAppAuditItemMapperImpl sut;

    @BeforeEach
    void setUp() {
        sut = new AuditItemAppAuditItemMapperImpl();
    }

    @Test
    void givenSomeAudiItem_whenFrom_thenSuccessAppAuditItem() {
        AuditAuditItem auditAuditItem = createAuditItem();

        AppAuditItem result = sut.from(auditAuditItem);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(auditAuditItem.getName());
        assertThat(result.getOccurredAt()).isEqualTo(auditAuditItem.getOccurredAt());
        assertThat(result.getUser()).isEqualTo(auditAuditItem.getUser());
    }

    @Test
    void givenSomeAudiItemList_whenFrom_thenSuccessAppAuditItemList() {
        List<AuditItem> auditItemList = List.of(createAuditItem());

        List<AppAuditItem> result = sut.from(auditItemList);

        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
    }


    private AuditAuditItem createAuditItem() {
        return AuditAuditItem.builder()
                .name("test-name")
                .occurredAt(Instant.now())
                .user(new AuditDomainUser(UUID.randomUUID()))
                .build();
    }
}