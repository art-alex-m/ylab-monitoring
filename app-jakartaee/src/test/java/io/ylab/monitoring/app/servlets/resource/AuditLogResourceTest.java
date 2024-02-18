package io.ylab.monitoring.app.servlets.resource;

import io.ylab.monitoring.app.jakartaee.resource.AuditLogResource;
import io.ylab.monitoring.app.jakartaee.service.AppUserContext;
import io.ylab.monitoring.audit.model.AuditDomainUser;
import io.ylab.monitoring.domain.audit.boundary.ViewAuditLogInput;
import io.ylab.monitoring.domain.audit.in.ViewAuditLogInputRequest;
import io.ylab.monitoring.domain.audit.model.AuditItem;
import io.ylab.monitoring.domain.audit.out.ViewAuditLogInputResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.times;
import static org.mockito.BDDMockito.verify;

@ExtendWith(MockitoExtension.class)
class AuditLogResourceTest {

    AuditLogResource sut;

    @Mock
    ViewAuditLogInput auditLogInteractor;

    @Mock
    AppUserContext userContext;

    @Mock
    ViewAuditLogInputResponse response;

    @BeforeEach
    void setUp() {
        sut = new AuditLogResource(auditLogInteractor, userContext);
    }

    @Test
    void auditLog() {
        given(userContext.getCurrentUser()).willReturn(AuditDomainUser.NULL_USER);
        given(auditLogInteractor.view(any())).willReturn(response);
        given(response.getAuditLog()).willReturn(Collections.emptyList());
        ArgumentCaptor<ViewAuditLogInputRequest> inputResponseCaptor = ArgumentCaptor.forClass(
                ViewAuditLogInputRequest.class);

        List<? extends AuditItem> result = sut.auditLog();

        assertThat(result).isNotNull().isEqualTo(Collections.emptyList());
        verify(auditLogInteractor, times(1)).view(inputResponseCaptor.capture());
        ViewAuditLogInputRequest request = inputResponseCaptor.getValue();
        assertThat(request).isNotNull();
        assertThat(request.getUser()).isEqualTo(AuditDomainUser.NULL_USER);
    }
}