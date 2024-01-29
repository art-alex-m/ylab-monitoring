package io.ylab.monitoring.audit.boundary;

import io.ylab.monitoring.domain.audit.in.CreateAuditLogInputRequest;
import io.ylab.monitoring.domain.audit.model.AuditItem;
import io.ylab.monitoring.domain.audit.repository.CreateAuditLogInputDbRepository;
import io.ylab.monitoring.domain.core.model.DomainUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class AuditCreateAuditLogInteractorTest {

    @Mock
    DomainUser domainUser;

    @Mock
    CreateAuditLogInputDbRepository inputDbRepository;

    @Mock
    CreateAuditLogInputRequest request;

    @InjectMocks
    AuditCreateAuditLogInteractor sut;

    @Test
    void givenGoodRequest_whenCreate_thenSuccess() {
        String testString = "test-string";
        Instant testOccurredAt = Instant.now();
        given(request.getUser()).willReturn(domainUser);
        given(request.getName()).willReturn(testString);
        given(request.getOccurredAt()).willReturn(testOccurredAt);
        ArgumentCaptor<AuditItem> auditCaptor = ArgumentCaptor.forClass(AuditItem.class);

        boolean result = sut.create(request);

        assertThat(result).isTrue();
        verify(inputDbRepository, times(1)).create(auditCaptor.capture());
        AuditItem auditItem = auditCaptor.getValue();
        assertThat(auditItem).isNotNull();
        assertThat(auditItem.getName()).isEqualTo(testString);
        assertThat(auditItem.getOccurredAt()).isEqualTo(testOccurredAt);
        assertThat(auditItem.getUser()).isEqualTo(domainUser);
    }
}
