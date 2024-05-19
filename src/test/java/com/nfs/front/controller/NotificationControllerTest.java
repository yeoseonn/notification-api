package com.nfs.front.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nfs.front.error.ApiControllerAdvice;
import com.nfs.front.model.Notification;
import com.nfs.front.model.NotificationRequest;
import com.nfs.front.model.NotificationType;
import com.nfs.front.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
class NotificationControllerTest {
    private MockMvc mockMvc;
    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private NotificationController notificationController;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(notificationController)
                .setControllerAdvice(new ApiControllerAdvice())
                .build();
    }

    @Test
    void registerNotification() throws Exception {
        NotificationRequest notificationRequest = new NotificationRequest(1004L, NotificationType.IMMEDIATE, null, "test", "test");

        String jsonValue = objectMapper.writeValueAsString(notificationRequest);
        Notification notification = new Notification(1111L, 1004L, NotificationType.IMMEDIATE, LocalDateTime.now(), "test", "test");
        when(notificationService.registerNotification(any(NotificationRequest.class))).thenReturn(notification);


        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.post("/notifications")
                                                                                            .content(jsonValue)
                                                                                            .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(mockHttpServletRequestBuilder)
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.header.isSuccessful").value(true))
               .andExpect(MockMvcResultMatchers.jsonPath("$.result.notificationId").value(notification.notificationId()))
               .andExpect(MockMvcResultMatchers.jsonPath("$.result.memberId").value(notification.memberId()));

    }
}