package io.ylab.monitoring.app.servlets.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.ylab.monitoring.app.servlets.component.HttpRequestAttribute;
import io.ylab.monitoring.app.servlets.config.AppConfiguration;
import io.ylab.monitoring.app.servlets.out.AppAuditItem;
import io.ylab.monitoring.app.servlets.service.AppUserContext;
import io.ylab.monitoring.app.servlets.support.TestServletUtils;
import io.ylab.monitoring.domain.audit.boundary.ViewAuditLogInput;
import io.ylab.monitoring.domain.audit.in.ViewAuditLogInputRequest;
import io.ylab.monitoring.domain.audit.out.ViewAuditLogInputResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.doReturn;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.times;
import static org.mockito.BDDMockito.verify;
import static org.mockito.BDDMockito.verifyNoMoreInteractions;
import static uk.org.webcompere.modelassert.json.JsonAssertions.assertJson;

@ExtendWith(MockitoExtension.class)
class AdminAuditServletTest {
    private final static AppAuditItem testAudit = new AppAuditItem(Instant.now(), TestServletUtils.domainUser,
            "test-audit-name");

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    ObjectMapper objectMapper = AppConfiguration.REGISTRY.objectMapper();

    @Mock
    PrintWriter printWriter;

    AppUserContext userContext = AppConfiguration.REGISTRY.appUserContext();

    @Mock
    ViewAuditLogInput auditLogInput;

    @Mock
    ViewAuditLogInputResponse inputResponse;

    AdminAuditServlet sut;

    @BeforeEach
    void setUp() throws IOException {
        sut = new AdminAuditServlet(objectMapper, userContext, auditLogInput);
        given(request.getAttribute(HttpRequestAttribute.PRINCIPAL)).willReturn(TestServletUtils.principal);
        given(auditLogInput.view(any())).willReturn(inputResponse);
        given(response.getWriter()).willReturn(printWriter);
    }

    @Test
    void givenRequest_whenDoGet_thenSuccess() throws IOException {
        doReturn(List.of(testAudit)).when(inputResponse).getAuditLog();
        ArgumentCaptor<ViewAuditLogInputRequest> inputRequestCaptor = ArgumentCaptor.forClass(
                ViewAuditLogInputRequest.class);
        ArgumentCaptor<String> outJsonCaptor = ArgumentCaptor.forClass(String.class);

        Throwable result = catchThrowable(() -> sut.doGet(request, response));

        assertThat(result).isNull();
        verify(auditLogInput, times(1)).view(inputRequestCaptor.capture());
        verifyNoMoreInteractions(auditLogInput);
        ViewAuditLogInputRequest inputRequest = inputRequestCaptor.getValue();
        assertThat(inputRequest).isNotNull();
        assertThat(inputRequest.getUser().getId()).isEqualTo(TestServletUtils.domainUser.getId());
        verify(response, times(1)).setContentType(TestServletUtils.mediaType);
        verify(response, times(1)).setCharacterEncoding(TestServletUtils.encoding);
        verify(response, times(1)).getWriter();
        verifyNoMoreInteractions(response);
        verify(printWriter, times(1)).print(outJsonCaptor.capture());
        String outJson = outJsonCaptor.getValue();
        System.out.println(outJson);
        assertJson(outJson).hasSize(1)
                .at("/0").containsKeysExactlyInAnyOrder("occurredAt", "user", "name");
        assertJson(outJson).at("/0/name").isText(testAudit.getName());
        assertJson(outJson).at("/0/user/id").isEqualTo(testAudit.getUser().getId());
        assertJson(outJson).at("/0/user").containsKeysExactlyInAnyOrder("id");
        assertJson(outJson).at("/0/occurredAt").isNumber();
    }
}