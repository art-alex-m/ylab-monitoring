package io.ylab.monitoring.audit.boundary;

import io.ylab.monitoring.domain.audit.event.ViewAuditLogEntered;
import io.ylab.monitoring.domain.audit.event.ViewAuditLogFound;
import io.ylab.monitoring.domain.audit.in.ViewAuditLogInputRequest;
import io.ylab.monitoring.domain.audit.out.ViewAuditLogInputResponse;
import io.ylab.monitoring.domain.audit.out.ViewAuditLogInputResponseFactory;
import io.ylab.monitoring.domain.audit.repository.ViewAuditLogInputDbRepository;
import io.ylab.monitoring.domain.core.event.MonitoringEventPublisher;
import io.ylab.monitoring.domain.core.model.DomainUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class AuditViewAuditLogInteractorTest {

    @Mock
    DomainUser domainUser;

    @Mock
    ViewAuditLogInputDbRepository inputDbRepository;

    @Mock
    ViewAuditLogInputResponseFactory responseFactory;

    @Mock
    MonitoringEventPublisher eventPublisher;

    @Mock
    ViewAuditLogInputResponse response;

    @Mock
    ViewAuditLogInputRequest request;

    @InjectMocks
    AuditViewAuditLogInteractor sut;

    @BeforeEach
    void setUp() {
        given(request.getUser()).willReturn(domainUser);
        given(responseFactory.create(Collections.emptyList())).willReturn(response);
    }

    @Test
    void givenGoodRequest_whenView_thenSuccess() {
        ArgumentCaptor<ViewAuditLogEntered> enteredCaptor = ArgumentCaptor.forClass(ViewAuditLogEntered.class);
        ArgumentCaptor<ViewAuditLogFound> foundCaptor = ArgumentCaptor.forClass(ViewAuditLogFound.class);

        ViewAuditLogInputResponse result = sut.view(request);

        assertThat(result).isNotNull();
        verify(eventPublisher, times(1)).publish(enteredCaptor.capture());
        verify(eventPublisher, times(1)).publish(foundCaptor.capture());
        verify(inputDbRepository, times(1)).findAll();
        ViewAuditLogEntered enteredEvent = enteredCaptor.getValue();
        ViewAuditLogFound foundEvent = foundCaptor.getValue();
        assertThat(enteredEvent).isNotNull();
        assertThat(enteredEvent.getRequest()).isNotNull().isEqualTo(request);
        assertThat(enteredEvent.getUser()).isNotNull().isEqualTo(domainUser);
        assertThat(enteredEvent.getCreatedAt()).isNotNull();
        assertThat(foundEvent).isNotNull();
        assertThat(foundEvent.getUser()).isNotNull().isEqualTo(domainUser);
        assertThat(foundEvent.getAuditLog()).isNotNull().isEqualTo(Collections.emptyList());
        assertThat(foundEvent.getCreatedAt()).isNotNull();
    }
}
