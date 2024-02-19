package io.ylab.monitoring.app.servlets.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.ylab.monitoring.app.servlets.component.HttpRequestAttribute;
import io.ylab.monitoring.app.servlets.config.AppConfiguration;
import io.ylab.monitoring.app.servlets.service.AppUserContext;
import io.ylab.monitoring.app.servlets.support.TestServletUtils;
import io.ylab.monitoring.core.model.CoreMeter;
import io.ylab.monitoring.domain.core.boundary.ViewMetersInput;
import io.ylab.monitoring.domain.core.in.ViewMetersInputRequest;
import io.ylab.monitoring.domain.core.model.Meter;
import io.ylab.monitoring.domain.core.out.ViewMetersInputResponse;
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
import java.util.List;
import java.util.UUID;

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
class MeterServletTest {

    private final static Meter testMeter = new CoreMeter(UUID.randomUUID(), "test-meter");

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    ObjectMapper objectMapper = AppConfiguration.REGISTRY.objectMapper();

    @Mock
    PrintWriter printWriter;

    AppUserContext userContext = AppConfiguration.REGISTRY.appUserContext();

    @Mock
    ViewMetersInput metersInput;

    @Mock
    ViewMetersInputResponse inputResponse;

    MeterServlet sut;

    @BeforeEach
    void setUp() throws IOException {
        sut = new MeterServlet(metersInput, objectMapper, userContext);
        given(request.getAttribute(HttpRequestAttribute.PRINCIPAL)).willReturn(TestServletUtils.principal);
        given(metersInput.find(any())).willReturn(inputResponse);
        given(response.getWriter()).willReturn(printWriter);
    }

    @Test
    void givenRequest_whenDoGet_thenSuccess() throws IOException {
        doReturn(List.of(testMeter)).when(inputResponse).getMeters();
        ArgumentCaptor<ViewMetersInputRequest> inputRequestCaptor = ArgumentCaptor.forClass(
                ViewMetersInputRequest.class);
        ArgumentCaptor<String> outJsonCaptor = ArgumentCaptor.forClass(String.class);

        Throwable result = catchThrowable(() -> sut.doGet(request, response));

        assertThat(result).isNull();
        verify(metersInput, times(1)).find(inputRequestCaptor.capture());
        verifyNoMoreInteractions(metersInput);
        ViewMetersInputRequest inputRequest = inputRequestCaptor.getValue();
        assertThat(inputRequest).isNotNull();
        assertThat(inputRequest.getUser()).isEqualTo(TestServletUtils.principal);
        verify(response, times(1)).setContentType(TestServletUtils.mediaType);
        verify(response, times(1)).setCharacterEncoding(TestServletUtils.encoding);
        verify(response, times(1)).getWriter();
        verifyNoMoreInteractions(response);
        verify(printWriter, times(1)).print(outJsonCaptor.capture());
        String outJson = outJsonCaptor.getValue();
        assertJson(outJson).hasSize(1)
                .at("/0").containsKeysExactlyInAnyOrder("id", "name");
        assertJson(outJson).at("/0/name").isText(testMeter.getName());
        assertJson(outJson).at("/0/id").isEqualTo(testMeter.getId());
    }
}