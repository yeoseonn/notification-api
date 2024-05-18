package com.nfs.front.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nfs.front.error.ApiControllerAdvice;
import com.nfs.front.model.NotificationLog;
import com.nfs.front.model.SenderType;
import com.nfs.front.service.NotificationLogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
class NotificationLogControllerTest {

    private MockMvc mockMvc;
    @Mock
    NotificationLogService notificationLogService;

    @InjectMocks
    NotificationLogController notificationLogController;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(notificationLogController)
                .setControllerAdvice(new ApiControllerAdvice())
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    void registerNotification() throws Exception {
        Long memberId = 1004L;
        NotificationLog notificationLog = new NotificationLog(122L, 111L, memberId, SenderType.SMS, LocalDateTime.now().minusMinutes(2));
        List<NotificationLog> notificationLogList = List.of(notificationLog);
        Page<NotificationLog> notificationLogs = new PageImpl<>(notificationLogList, PageRequest.of(0, 20), notificationLogList.size());

        when(notificationLogService.getNotificationLogsByMemberId(anyLong(), any(Pageable.class))).thenReturn(notificationLogs);
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.get("/notifications/logs")
                                                                                            .param("memberId", String.valueOf(memberId))
                                                                                            .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockHttpServletRequestBuilder)
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.header.isSuccessful").value(true))
               .andExpect(MockMvcResultMatchers.jsonPath("$.totalCount").value(notificationLogs.getTotalElements()))
               .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].memberId").value(memberId));



    }
}