package io.ylab.monitoring.app.servlets.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.ylab.monitoring.app.servlets.component.HttpRequestAttribute;
import io.ylab.monitoring.app.servlets.config.AppConfiguration;
import io.ylab.monitoring.app.servlets.in.AppMonthReadingRequest;
import io.ylab.monitoring.app.servlets.service.AppUserContext;
import io.ylab.monitoring.app.servlets.service.AppValidationService;
import io.ylab.monitoring.app.servlets.support.TestServletUtils;
import io.ylab.monitoring.domain.core.boundary.GetMonthMeterReadingsInput;
import io.ylab.monitoring.domain.core.in.GetMonthMeterReadingsInputRequest;
import io.ylab.monitoring.domain.core.out.GetMonthMeterReadingsResponse;
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
import java.time.LocalDateTime;
import java.time.ZoneOffset;
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
class UserReadingsMonthServletTest {
    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    ObjectMapper objectMapper = AppConfiguration.REGISTRY.objectMapper();

    @Mock
    PrintWriter printWriter;

    AppUserContext userContext = AppConfiguration.REGISTRY.appUserContext();

    @Mock
    AppValidationService validationService;

    @Mock
    GetMonthMeterReadingsInput monthInput;

    @Mock
    GetMonthMeterReadingsResponse inputResponse;

    UserReadingsMonthServlet sut;

    @BeforeEach
    void setUp() throws IOException {
        sut = new UserReadingsMonthServlet(objectMapper, userContext, monthInput, validationService);
        given(request.getAttribute(HttpRequestAttribute.PRINCIPAL)).willReturn(TestServletUtils.principal);
        given(request.getParameter("month")).willReturn(TestServletUtils.testMonth);
        given(request.getParameter("year")).willReturn(TestServletUtils.testYear);
        given(monthInput.find(any())).willReturn(inputResponse);
        given(response.getWriter()).willReturn(printWriter);
    }

    @Test
    void givenRequest_whenDoGet_thenSuccess() throws IOException {
        doReturn(List.of(TestServletUtils.testReading)).when(inputResponse).getMeterReadings();
        ArgumentCaptor<AppMonthReadingRequest> appRequestCaptor = ArgumentCaptor.forClass(AppMonthReadingRequest.class);
        ArgumentCaptor<GetMonthMeterReadingsInputRequest> inputRequestCaptor = ArgumentCaptor.forClass(
                GetMonthMeterReadingsInputRequest.class);
        ArgumentCaptor<String> outJsonCaptor = ArgumentCaptor.forClass(String.class);

        Throwable result = catchThrowable(() -> sut.doGet(request, response));

        assertThat(result).isNull();
        verify(validationService, times(1)).validate(appRequestCaptor.capture());
        verifyNoMoreInteractions(validationService);
        AppMonthReadingRequest appRequest = appRequestCaptor.getValue();
        assertThat(appRequest).isNotNull();
        assertThat(appRequest.getMonth()).isEqualTo(Integer.parseInt(TestServletUtils.testMonth));
        assertThat(appRequest.getYear()).isEqualTo(Integer.parseInt(TestServletUtils.testYear));
        verify(monthInput, times(1)).find(inputRequestCaptor.capture());
        verifyNoMoreInteractions(monthInput);
        GetMonthMeterReadingsInputRequest inputRequest = inputRequestCaptor.getValue();
        assertThat(inputRequest).isNotNull();
        assertThat(inputRequest.getUser().getId()).isEqualTo(TestServletUtils.domainUser.getId());
        assertThat(inputRequest.getPeriod()).isEqualTo(
                LocalDateTime.parse("2024-02-01T00:00:00.000").toInstant(ZoneOffset.UTC));
        verify(response, times(1)).setContentType(TestServletUtils.mediaType);
        verify(response, times(1)).setCharacterEncoding(TestServletUtils.encoding);
        verify(response, times(1)).getWriter();
        verifyNoMoreInteractions(response);
        verify(printWriter, times(1)).print(outJsonCaptor.capture());
        String outJson = outJsonCaptor.getValue();
        System.out.println(outJson);
        assertJson(outJson).hasSize(1)
                .at("/0").containsKeysExactlyInAnyOrder("user", "period", "meter", "value", "id", "createdAt");
        assertJson(outJson).at("/0/user/id").isEqualTo(TestServletUtils.domainUser.getId());
        assertJson(outJson).at("/0/user").containsKeysExactlyInAnyOrder("id");
        assertJson(outJson).at("/0/createdAt").isNumber();
        assertJson(outJson).at("/0/period").isNumber();
        assertJson(outJson).at("/0/id").isEqualTo(TestServletUtils.testReading.getId());
        assertJson(outJson).at("/0/value").isNumberEqualTo(TestServletUtils.testReading.getValue());
        assertJson(outJson).at("/0/meter").containsKeysExactlyInAnyOrder("id", "name");
        assertJson(outJson).at("/0/meter/name").isText(TestServletUtils.testReading.getMeter().getName());
        assertJson(outJson).at("/0/meter/id").isEqualTo(TestServletUtils.testReading.getMeter().getId());
    }
}