package io.ylab.monitoring.app.springboot.controller;

import io.ylab.monitoring.app.springboot.out.AppMeter;
import io.ylab.monitoring.app.springboot.out.AppMeterReading;
import io.ylab.monitoring.app.springboot.service.AppUserContext;
import io.ylab.monitoring.core.model.CoreDomainUser;
import io.ylab.monitoring.domain.core.boundary.GetActualMeterReadingsInput;
import io.ylab.monitoring.domain.core.boundary.GetMonthMeterReadingsInput;
import io.ylab.monitoring.domain.core.boundary.ViewMeterReadingsHistoryInput;
import io.ylab.monitoring.domain.core.in.GetActualMeterReadingsInputRequest;
import io.ylab.monitoring.domain.core.in.GetMonthMeterReadingsInputRequest;
import io.ylab.monitoring.domain.core.in.ViewMeterReadingsHistoryInputRequest;
import io.ylab.monitoring.domain.core.model.DomainUser;
import io.ylab.monitoring.domain.core.out.GetActualMeterReadingsInputResponse;
import io.ylab.monitoring.domain.core.out.GetMonthMeterReadingsResponse;
import io.ylab.monitoring.domain.core.out.ViewMeterReadingsHistoryInputResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.doReturn;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.times;
import static org.mockito.BDDMockito.verify;
import static org.mockito.BDDMockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static uk.org.webcompere.modelassert.json.JsonAssertions.assertJson;

/**
 * AdminReadingControllerTest
 *
 * <p>
 * <a href="https://www.baeldung.com/json-modelassert">Guide to the ModelAssert Library for JSON</a><br>
 * <a href="https://docs.spring.io/spring-framework/reference/testing/spring-mvc-test-framework/server-performing-requests.html">Spring MockMvc Performing Requests</a><br>
 * </p>
 */
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(value = AdminReadingController.class, excludeAutoConfiguration = UserDetailsServiceAutoConfiguration.class)
class AdminReadingControllerTest {

    private static final String TEST_ROUTE = "/api/admin/readings";
    private static final String TEST_ROUTE_ACTUAL = "/api/admin/readings/actual";
    private static final String TEST_ROUTE_MONTH = "/api/admin/readings/month";

    private static final DomainUser testUser = new CoreDomainUser(UUID.randomUUID());

    private static final AppMeter testMeter = new AppMeter(UUID.randomUUID(), "test-meter");

    private static final AppMeterReading testReading = new AppMeterReading(testUser, Instant.now(), testMeter, 2L,
            UUID.randomUUID(), Instant.now());

    @Autowired
    MockMvc mockMvc;

    @MockBean(name = "appUserContext")
    AppUserContext userContext;

    @MockBean(name = "adminActualMeterReadingsInteractor")
    GetActualMeterReadingsInput actualReadingInteractor;

    @MockBean
    GetActualMeterReadingsInputResponse actualMeterReadingsInputResponse;

    @MockBean(name = "adminMeterReadingsHistoryInteractor")
    ViewMeterReadingsHistoryInput historyInteractor;

    @MockBean
    ViewMeterReadingsHistoryInputResponse meterReadingsHistoryInputResponse;

    @MockBean(name = "adminMonthMeterReadingsInteractor")
    GetMonthMeterReadingsInput monthInteractor;

    @MockBean
    GetMonthMeterReadingsResponse monthMeterReadingsResponse;

    @BeforeEach
    void setUp() {
        given(userContext.getCurrentUser()).willReturn(testUser);
    }

    @Test
    void givenRequest_whenActual_thenSuccess() throws Exception {
        doReturn(List.of(testReading)).when(actualMeterReadingsInputResponse).getMeterReadings();
        given(actualReadingInteractor.find(any(GetActualMeterReadingsInputRequest.class))).willReturn(
                actualMeterReadingsInputResponse);
        ArgumentCaptor<GetActualMeterReadingsInputRequest> inputRequestArgumentCaptor = ArgumentCaptor.forClass(
                GetActualMeterReadingsInputRequest.class);


        MvcResult result = mockMvc.perform(
                        get(TEST_ROUTE_ACTUAL).characterEncoding(StandardCharsets.UTF_8))
                .andReturn();


        assertThat(result).isNotNull();
        assertThat(result.getResponse().getStatus()).isEqualTo(200);
        assertThat(result.getResponse().getContentType()).isEqualTo(MediaType.APPLICATION_JSON_VALUE);
        verify(userContext, times(1)).getCurrentUser();
        verifyNoMoreInteractions(userContext);
        verify(actualReadingInteractor, times(1)).find(inputRequestArgumentCaptor.capture());
        verifyNoMoreInteractions(actualReadingInteractor);
        GetActualMeterReadingsInputRequest inputRequest = inputRequestArgumentCaptor.getValue();
        assertThat(inputRequest).isNotNull();
        assertThat(inputRequest.getUser()).isEqualTo(testUser);
        String jsonContent = result.getResponse().getContentAsString();
        assertAll(
                () -> assertJson(jsonContent).isNotEmpty().hasSize(1)
                        .at("/0").containsKeysExactlyInAnyOrder("user", "period", "meter", "value", "id", "createdAt"),
                () -> assertJson(jsonContent).at("/0/user/id").isEqualTo(testUser.getId()),
                () -> assertJson(jsonContent).at("/0/meter/id").isEqualTo(testMeter.getId()),
                () -> assertJson(jsonContent).at("/0/meter/name").isText(testMeter.getName()),
                () -> assertJson(jsonContent).at("/0/value").isNumberEqualTo(testReading.getValue()),
                () -> assertJson(jsonContent).at("/0/id").isEqualTo(testReading.getId()),
                () -> assertJson(jsonContent).at("/0/createdAt").isNotEmpty(),
                () -> assertJson(jsonContent).at("/0/period").isNotEmpty()
        );
    }

    @Test
    void givenRequest_whenHistory_thenSuccess() throws Exception {
        doReturn(List.of(testReading)).when(meterReadingsHistoryInputResponse).getMeterReadings();
        given(historyInteractor.find(any(ViewMeterReadingsHistoryInputRequest.class))).willReturn(
                meterReadingsHistoryInputResponse);
        ArgumentCaptor<ViewMeterReadingsHistoryInputRequest> inputRequestArgumentCaptor = ArgumentCaptor.forClass(
                ViewMeterReadingsHistoryInputRequest.class);


        MvcResult result = mockMvc.perform(
                        get(TEST_ROUTE).characterEncoding(StandardCharsets.UTF_8))
                .andReturn();


        assertThat(result).isNotNull();
        assertThat(result.getResponse().getStatus()).isEqualTo(200);
        assertThat(result.getResponse().getContentType()).isEqualTo(MediaType.APPLICATION_JSON_VALUE);
        verify(userContext, times(1)).getCurrentUser();
        verifyNoMoreInteractions(userContext);
        verify(historyInteractor, times(1)).find(inputRequestArgumentCaptor.capture());
        verifyNoMoreInteractions(historyInteractor);
        ViewMeterReadingsHistoryInputRequest inputRequest = inputRequestArgumentCaptor.getValue();
        assertThat(inputRequest).isNotNull();
        assertThat(inputRequest.getUser()).isEqualTo(testUser);
        String jsonContent = result.getResponse().getContentAsString();
        assertAll(
                () -> assertJson(jsonContent).isNotEmpty().hasSize(1)
                        .at("/0").containsKeysExactlyInAnyOrder("user", "period", "meter", "value", "id", "createdAt"),
                () -> assertJson(jsonContent).at("/0/user/id").isEqualTo(testUser.getId()),
                () -> assertJson(jsonContent).at("/0/meter/id").isEqualTo(testMeter.getId()),
                () -> assertJson(jsonContent).at("/0/meter/name").isText(testMeter.getName()),
                () -> assertJson(jsonContent).at("/0/value").isNumberEqualTo(testReading.getValue()),
                () -> assertJson(jsonContent).at("/0/id").isEqualTo(testReading.getId()),
                () -> assertJson(jsonContent).at("/0/createdAt").isNotEmpty(),
                () -> assertJson(jsonContent).at("/0/period").isNotEmpty()
        );
    }

    @Test
    void givenRequest_whenMonth_thenSuccess() throws Exception {
        doReturn(List.of(testReading)).when(monthMeterReadingsResponse).getMeterReadings();
        given(monthInteractor.find(any(GetMonthMeterReadingsInputRequest.class))).willReturn(
                monthMeterReadingsResponse);
        ArgumentCaptor<GetMonthMeterReadingsInputRequest> inputRequestArgumentCaptor = ArgumentCaptor.forClass(
                GetMonthMeterReadingsInputRequest.class);


        MvcResult result = mockMvc.perform(get(TEST_ROUTE_MONTH)
                        .param("month", "2").param("year", "2023")
                        .characterEncoding(StandardCharsets.UTF_8))
                .andReturn();


        assertThat(result).isNotNull();
        assertThat(result.getResponse().getStatus()).isEqualTo(200);
        assertThat(result.getResponse().getContentType()).isEqualTo(MediaType.APPLICATION_JSON_VALUE);
        verify(userContext, times(1)).getCurrentUser();
        verifyNoMoreInteractions(userContext);
        verify(monthInteractor, times(1)).find(inputRequestArgumentCaptor.capture());
        verifyNoMoreInteractions(monthInteractor);
        GetMonthMeterReadingsInputRequest inputRequest = inputRequestArgumentCaptor.getValue();
        assertThat(inputRequest).isNotNull();
        assertThat(inputRequest.getUser()).isEqualTo(testUser);
        assertThat(inputRequest.getPeriod()).isNotNull()
                .isEqualTo(LocalDateTime.parse("2023-02-01T00:00:00.000").toInstant(ZoneOffset.UTC));
        String jsonContent = result.getResponse().getContentAsString();
        assertAll(
                () -> assertJson(jsonContent).isNotEmpty().hasSize(1)
                        .at("/0").containsKeysExactlyInAnyOrder("user", "period", "meter", "value", "id", "createdAt"),
                () -> assertJson(jsonContent).at("/0/user/id").isEqualTo(testUser.getId()),
                () -> assertJson(jsonContent).at("/0/meter/id").isEqualTo(testMeter.getId()),
                () -> assertJson(jsonContent).at("/0/meter/name").isText(testMeter.getName()),
                () -> assertJson(jsonContent).at("/0/value").isNumberEqualTo(testReading.getValue()),
                () -> assertJson(jsonContent).at("/0/id").isEqualTo(testReading.getId()),
                () -> assertJson(jsonContent).at("/0/createdAt").isNotEmpty(),
                () -> assertJson(jsonContent).at("/0/period").isNotEmpty()
        );
    }
}
