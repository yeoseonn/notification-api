package com.nfs.front.job;

import com.nfs.front.entity.MemberEntity;
import com.nfs.front.entity.NotificationEntity;
import com.nfs.front.entity.NotificationItemEntity;
import com.nfs.front.model.NotificationType;
import com.nfs.front.repository.MemberRepository;
import com.nfs.front.repository.NotificationItemRepository;
import com.nfs.front.repository.NotificationRepository;
import com.nfs.front.service.NotificationSendService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@SpringBatchTest
class NotificationJobConfigTest {

    private JobLauncherTestUtils jobLauncherTestUtils;
    @Autowired
    private NotificationItemRepository notificationItemRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private JobRepository jobRepository;


    @Autowired
    private Job notifyTriggerJob;

    @BeforeEach
    void setUp() {
        this.jobLauncherTestUtils = new JobLauncherTestUtils();
        this.jobLauncherTestUtils.setJob(notifyTriggerJob);
        this.jobLauncherTestUtils.setJobLauncher(jobLauncher);
        this.jobLauncherTestUtils.setJobRepository(jobRepository);
    }


    @Test
    public void testNotificationJob() throws Exception {

        MemberEntity memberEntity = new MemberEntity(33L, "01013223344", "test", "yeoseon.yun@kakao.com", LocalDateTime.now());
        memberRepository.save(memberEntity);

        NotificationEntity notification = new NotificationEntity(11L, memberEntity.getMemberId(), NotificationType.SCHEDULED, LocalDateTime.now().plusSeconds(20), "test", "test", LocalDateTime.now());
        notificationRepository.save(notification);

        NotificationItemEntity notificationItemEntity = new NotificationItemEntity(11L, notification.getNotificationId(), notification.getTime());
        notificationItemRepository.save(notificationItemEntity);

        JobParameters jobParameters = new JobParametersBuilder()
                .addString("now", LocalDateTime.now().toString())
                .toJobParameters();

        jobLauncherTestUtils.setJob(jobLauncherTestUtils.getJob());
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

        assertEquals(jobExecution.getStatus(), BatchStatus.COMPLETED);

        List<NotificationItemEntity> notificationItemEntityList = notificationItemRepository.findAll();
        assertEquals(0, notificationItemEntityList.size());
    }


}