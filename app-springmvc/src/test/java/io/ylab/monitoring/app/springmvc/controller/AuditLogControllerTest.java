package io.ylab.monitoring.app.springmvc.controller;

import io.ylab.monitoring.app.springmvc.out.AppAuditItem;
import io.ylab.monitoring.app.springmvc.service.AppUserContext;
import io.ylab.monitoring.app.springmvc.support.MockBean;
import io.ylab.monitoring.app.springmvc.support.MockMvcTest;
import io.ylab.monitoring.core.model.CoreDomainUser;
import io.ylab.monitoring.domain.audit.boundary.ViewAuditLogInput;
import io.ylab.monitoring.domain.audit.in.ViewAuditLogInputRequest;
import io.ylab.monitoring.domain.audit.model.AuditItem;
import io.ylab.monitoring.domain.audit.out.ViewAuditLogInputResponse;
import io.ylab.monitoring.domain.core.model.DomainUser;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.doReturn;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.times;
import static org.mockito.BDDMockito.verify;
import static org.mockito.BDDMockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static uk.org.webcompere.modelassert.json.JsonAssertions.assertJson;

@MockMvcTest(AuditLogController.class)
class AuditLogControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ViewAuditLogInput auditLogInteractor;

    @MockBean
    AppUserContext userContext;

    @MockBean(contextAble = false)
    ViewAuditLogInputResponse response;

    @Test
    void givenRequest_whenAuditLog_thenEmptySuccess() throws Exception {
        DomainUser domainUser = new CoreDomainUser(UUID.randomUUID());
        given(userContext.getCurrentUser()).willReturn(domainUser);
        given(response.getAuditLog()).willReturn(Collections.emptyList());
        given(auditLogInteractor.view(any(ViewAuditLogInputRequest.class))).willReturn(response);
        ArgumentCaptor<ViewAuditLogInputRequest> inputRequestArgumentCaptor = ArgumentCaptor.forClass(
                ViewAuditLogInputRequest.class);

        MvcResult result = mockMvc.perform(
                        get("/admin/audit-logs").characterEncoding(StandardCharsets.UTF_8))
                .andReturn();

        assertThat(result).isNotNull();
        assertThat(result.getResponse().getStatus()).isEqualTo(200);
        assertThat(result.getResponse().getContentType()).isEqualTo(MediaType.APPLICATION_JSON_VALUE);
        verify(auditLogInteractor, times(1)).view(inputRequestArgumentCaptor.capture());
        verifyNoMoreInteractions(auditLogInteractor);
        ViewAuditLogInputRequest inputRequest = inputRequestArgumentCaptor.getValue();
        assertThat(inputRequest).isNotNull();
        assertThat(inputRequest.getUser()).isEqualTo(domainUser);
        assertThat(result.getResponse().getContentAsString()).isEqualTo("[]");
        verify(userContext, times(1)).getCurrentUser();
        verifyNoMoreInteractions(userContext);
    }

    @Test
    void givenRequest_whenAuditLog_thenListSuccess() throws Exception {
        DomainUser domainUser = new CoreDomainUser(UUID.randomUUID());
        AuditItem testItem = new AppAuditItem(Instant.now(), domainUser, "Some test string");
        given(userContext.getCurrentUser()).willReturn(domainUser);
        doReturn(List.of(testItem)).when(response).getAuditLog();
        given(auditLogInteractor.view(any(ViewAuditLogInputRequest.class))).willReturn(response);
        ArgumentCaptor<ViewAuditLogInputRequest> inputRequestArgumentCaptor = ArgumentCaptor.forClass(
                ViewAuditLogInputRequest.class);

        MvcResult result = mockMvc.perform(
                        get("/admin/audit-logs").characterEncoding(StandardCharsets.UTF_8))
                .andReturn();

        assertThat(result).isNotNull();
        assertThat(result.getResponse().getStatus()).isEqualTo(200);
        assertThat(result.getResponse().getContentType()).isEqualTo(MediaType.APPLICATION_JSON_VALUE);
        verify(auditLogInteractor, times(1)).view(inputRequestArgumentCaptor.capture());
        verifyNoMoreInteractions(auditLogInteractor);
        verify(userContext, times(1)).getCurrentUser();
        verifyNoMoreInteractions(userContext);
        ViewAuditLogInputRequest inputRequest = inputRequestArgumentCaptor.getValue();
        assertThat(inputRequest).isNotNull();
        assertThat(inputRequest.getUser()).isEqualTo(domainUser);
        assertThat(result.getResponse().getContentAsString()).isNotEmpty();
        String jsonContent = result.getResponse().getContentAsString();
        assertJson(jsonContent).hasSize(1)
                .at("/0").containsKeysExactlyInAnyOrder("occurredAt", "user", "name");
        assertJson(jsonContent).at("/0/occurredAt").isNumber();
        assertJson(jsonContent).at("/0/user/id").isEqualTo(domainUser.getId());
        assertJson(jsonContent).at("/0/name").isText(testItem.getName());
    }
}
