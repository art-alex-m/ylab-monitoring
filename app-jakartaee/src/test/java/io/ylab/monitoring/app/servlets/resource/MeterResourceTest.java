package io.ylab.monitoring.app.servlets.resource;

import io.ylab.monitoring.app.jakartaee.resource.MeterResource;
import io.ylab.monitoring.app.jakartaee.service.AppUserContext;
import io.ylab.monitoring.audit.model.AuditDomainUser;
import io.ylab.monitoring.domain.core.boundary.ViewMetersInput;
import io.ylab.monitoring.domain.core.in.ViewMetersInputRequest;
import io.ylab.monitoring.domain.core.model.Meter;
import io.ylab.monitoring.domain.core.out.ViewMetersInputResponse;
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
class MeterResourceTest {

    MeterResource sut;

    @Mock
    ViewMetersInput metersInteractor;

    @Mock
    AppUserContext userContext;

    @Mock
    ViewMetersInputResponse response;

    @BeforeEach
    void setUp() {
        sut = new MeterResource(metersInteractor, userContext);
    }

    @Test
    void givenCall_whenListMeters_thenSuccess() {
        given(userContext.getCurrentUser()).willReturn(AuditDomainUser.NULL_USER);
        given(metersInteractor.find(any())).willReturn(response);
        given(response.getMeters()).willReturn(Collections.emptyList());
        ArgumentCaptor<ViewMetersInputRequest> inputResponseCaptor = ArgumentCaptor.forClass(
                ViewMetersInputRequest.class);

        List<? extends Meter> result = sut.listMeters();

        assertThat(result).isNotNull().isEqualTo(Collections.emptyList());
        verify(metersInteractor, times(1)).find(inputResponseCaptor.capture());
        ViewMetersInputRequest request = inputResponseCaptor.getValue();
        assertThat(request).isNotNull();
        assertThat(request.getUser()).isEqualTo(AuditDomainUser.NULL_USER);
    }
}