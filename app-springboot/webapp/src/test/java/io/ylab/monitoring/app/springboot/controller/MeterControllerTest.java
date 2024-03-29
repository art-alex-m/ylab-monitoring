package io.ylab.monitoring.app.springboot.controller;

import io.ylab.monitoring.app.springboot.out.AppMeter;
import io.ylab.monitoring.app.springboot.service.AppUserContext;
import io.ylab.monitoring.core.model.CoreDomainUser;
import io.ylab.monitoring.domain.core.boundary.ViewMetersInput;
import io.ylab.monitoring.domain.core.in.ViewMetersInputRequest;
import io.ylab.monitoring.domain.core.model.DomainUser;
import io.ylab.monitoring.domain.core.out.ViewMetersInputResponse;
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
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.times;
import static org.mockito.BDDMockito.verify;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static uk.org.webcompere.modelassert.json.JsonAssertions.assertJson;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = MeterController.class, excludeAutoConfiguration = UserDetailsServiceAutoConfiguration.class)
class MeterControllerTest {

    private static final String TEST_ROUTE = "/api/meters";

    @Autowired
    MockMvc mockMvc;

    @MockBean(name = "appUserViewMetersInput")
    ViewMetersInput metersInteractor;

    @MockBean(name = "appUserContext")
    AppUserContext userContext;

    @MockBean
    ViewMetersInputResponse response;

    @Test
    void givenRequest_whenListMeters_thenSuccess() throws Exception {
        DomainUser domainUser = new CoreDomainUser(UUID.randomUUID());
        given(userContext.getCurrentUser()).willReturn(domainUser);
        given(response.getMeters()).willReturn(Collections.emptyList());
        given(metersInteractor.find(any(ViewMetersInputRequest.class))).willReturn(response);
        ArgumentCaptor<ViewMetersInputRequest> inputRequestArgumentCaptor = ArgumentCaptor.forClass(
                ViewMetersInputRequest.class);


        MvcResult result = mockMvc.perform(
                        get(TEST_ROUTE).characterEncoding(StandardCharsets.UTF_8))
                .andReturn();


        assertThat(result).isNotNull();
        assertThat(result.getResponse().getStatus()).isEqualTo(200);
        assertThat(result.getResponse().getContentType()).isEqualTo(MediaType.APPLICATION_JSON_VALUE);
        verify(metersInteractor, times(1)).find(inputRequestArgumentCaptor.capture());
        verifyNoMoreInteractions(metersInteractor);
        ViewMetersInputRequest inputRequest = inputRequestArgumentCaptor.getValue();
        assertThat(inputRequest).isNotNull();
        assertThat(inputRequest.getUser()).isEqualTo(domainUser);
        assertThat(result.getResponse().getContentAsString()).isEqualTo("[]");
        verify(userContext, times(1)).getCurrentUser();
        verifyNoMoreInteractions(userContext);
    }

    @Test
    void givenRequest_whenListMeters_thenListSuccess() throws Exception {
        DomainUser domainUser = new CoreDomainUser(UUID.randomUUID());
        AppMeter meter = new AppMeter(UUID.randomUUID(), "test-meter");
        given(userContext.getCurrentUser()).willReturn(domainUser);
        doReturn(List.of(meter)).when(response).getMeters();
        given(metersInteractor.find(any(ViewMetersInputRequest.class))).willReturn(response);
        ArgumentCaptor<ViewMetersInputRequest> inputRequestArgumentCaptor = ArgumentCaptor.forClass(
                ViewMetersInputRequest.class);


        MvcResult result = mockMvc.perform(
                        get(TEST_ROUTE).characterEncoding(StandardCharsets.UTF_8))
                .andReturn();


        assertThat(result).isNotNull();
        assertThat(result.getResponse().getStatus()).isEqualTo(200);
        assertThat(result.getResponse().getContentType()).isEqualTo(MediaType.APPLICATION_JSON_VALUE);
        verify(metersInteractor, times(1)).find(inputRequestArgumentCaptor.capture());
        verifyNoMoreInteractions(metersInteractor);
        ViewMetersInputRequest inputRequest = inputRequestArgumentCaptor.getValue();
        assertThat(inputRequest).isNotNull();
        assertThat(inputRequest.getUser()).isEqualTo(domainUser);
        verify(userContext, times(1)).getCurrentUser();
        verifyNoMoreInteractions(userContext);
        String jsonContent = result.getResponse().getContentAsString();
        assertJson(jsonContent).hasSize(1)
                .at("/0").containsKeysExactlyInAnyOrder("id", "name");
        assertJson(jsonContent).at("/0/id").isEqualTo(meter.getId());
        assertJson(jsonContent).at("/0/name").isText(meter.getName());
    }
}
