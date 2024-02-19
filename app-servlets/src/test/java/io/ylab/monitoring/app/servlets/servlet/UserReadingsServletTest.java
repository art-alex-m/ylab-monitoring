package io.ylab.monitoring.app.servlets.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.ylab.monitoring.app.servlets.component.HttpRequestAttribute;
import io.ylab.monitoring.app.servlets.config.AppConfiguration;
import io.ylab.monitoring.app.servlets.in.AppSubmitReadingRequest;
import io.ylab.monitoring.app.servlets.service.AppUserContext;
import io.ylab.monitoring.app.servlets.service.AppValidationService;
import io.ylab.monitoring.app.servlets.support.TestServletInputStream;
import io.ylab.monitoring.app.servlets.support.TestServletUtils;
import io.ylab.monitoring.domain.core.boundary.SubmissionMeterReadingsInput;
import io.ylab.monitoring.domain.core.boundary.ViewMeterReadingsHistoryInput;
import io.ylab.monitoring.domain.core.in.SubmissionMeterReadingsInputRequest;
import io.ylab.monitoring.domain.core.in.ViewMeterReadingsHistoryInputRequest;
import io.ylab.monitoring.domain.core.out.SubmissionMeterReadingsInputResponse;
import io.ylab.monitoring.domain.core.out.ViewMeterReadingsHistoryInputResponse;
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
class UserReadingsServletTest {

    private static final String submissionJson = "{" +
            " \"meter_name\": \"teplo\"," +
            " \"value\": 4," +
            " \"month\": 2," +
            " \"year\": 2024" +
            "}";

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    ObjectMapper objectMapper = AppConfiguration.REGISTRY.objectMapper();

    @Mock
    PrintWriter printWriter;

    AppUserContext userContext = AppConfiguration.REGISTRY.appUserContext();

    @Mock
    ViewMeterReadingsHistoryInput historyInput;

    @Mock
    ViewMeterReadingsHistoryInputResponse inputResponse;

    @Mock
    SubmissionMeterReadingsInput submissionInput;

    @Mock
    SubmissionMeterReadingsInputResponse submissionResponse;

    @Mock
    AppValidationService validationService;

    UserReadingsServlet sut;

    @BeforeEach
    void setUp() throws IOException {
        sut = new UserReadingsServlet(objectMapper, historyInput, submissionInput, userContext, validationService);
        given(request.getAttribute(HttpRequestAttribute.PRINCIPAL)).willReturn(TestServletUtils.principal);
        given(response.getWriter()).willReturn(printWriter);
    }

    @Test
    void givenRequest_whenDoGet_thenSuccess() throws IOException {
        given(historyInput.find(any())).willReturn(inputResponse);
        doReturn(List.of(TestServletUtils.testReading)).when(inputResponse).getMeterReadings();
        ArgumentCaptor<ViewMeterReadingsHistoryInputRequest> inputRequestCaptor = ArgumentCaptor.forClass(
                ViewMeterReadingsHistoryInputRequest.class);
        ArgumentCaptor<String> outJsonCaptor = ArgumentCaptor.forClass(String.class);

        Throwable result = catchThrowable(() -> sut.doGet(request, response));

        assertThat(result).isNull();
        verify(historyInput, times(1)).find(inputRequestCaptor.capture());
        verifyNoMoreInteractions(historyInput);
        ViewMeterReadingsHistoryInputRequest inputRequest = inputRequestCaptor.getValue();
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

    @Test
    void givenNewReading_whenDoPost_thenSuccess() throws IOException {
        given(request.getInputStream()).willReturn(new TestServletInputStream(submissionJson));
        given(submissionResponse.getMeterReading()).willReturn(TestServletUtils.testReading);
        given(submissionInput.submit(any())).willReturn(submissionResponse);
        ArgumentCaptor<AppSubmitReadingRequest> appRequestCaptor = ArgumentCaptor.forClass(
                AppSubmitReadingRequest.class);
        ArgumentCaptor<SubmissionMeterReadingsInputRequest> inputRequestCaptor = ArgumentCaptor.forClass(
                SubmissionMeterReadingsInputRequest.class);
        ArgumentCaptor<String> outJsonCaptor = ArgumentCaptor.forClass(String.class);

        Throwable result = catchThrowable(() -> sut.doPost(request, response));

        assertThat(result).isNull();
        verify(validationService, times(1)).validate(appRequestCaptor.capture());
        verifyNoMoreInteractions(validationService);
        AppSubmitReadingRequest appRequest = appRequestCaptor.getValue();
        assertThat(appRequest).isNotNull();
        assertThat(appRequest.getMonth()).isEqualTo(Integer.parseInt(TestServletUtils.testMonth));
        assertThat(appRequest.getYear()).isEqualTo(Integer.parseInt(TestServletUtils.testYear));
        verify(submissionInput, times(1)).submit(inputRequestCaptor.capture());
        verifyNoMoreInteractions(submissionInput);
        SubmissionMeterReadingsInputRequest inputRequest = inputRequestCaptor.getValue();
        assertThat(inputRequest).isNotNull();
        assertThat(inputRequest.getValue()).isEqualTo(4L);
        assertThat(inputRequest.getMeterName()).isEqualTo("teplo");
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
        assertJson(outJson).containsKeysExactlyInAnyOrder("user", "period", "meter", "value", "id", "createdAt");
        assertJson(outJson).at("/user/id").isEqualTo(TestServletUtils.domainUser.getId());
        assertJson(outJson).at("/user").containsKeysExactlyInAnyOrder("id");
        assertJson(outJson).at("/createdAt").isNumber();
        assertJson(outJson).at("/period").isNumber();
        assertJson(outJson).at("/id").isEqualTo(TestServletUtils.testReading.getId());
        assertJson(outJson).at("/value").isNumberEqualTo(TestServletUtils.testReading.getValue());
        assertJson(outJson).at("/meter").containsKeysExactlyInAnyOrder("id", "name");
        assertJson(outJson).at("/meter/name").isText(TestServletUtils.testReading.getMeter().getName());
        assertJson(outJson).at("/meter/id").isEqualTo(TestServletUtils.testReading.getMeter().getId());
    }
}
