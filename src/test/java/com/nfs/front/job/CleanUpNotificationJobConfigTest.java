package com.nfs.front.job;

import com.nfs.front.entity.MemberEntity;
import com.nfs.front.entity.NotificationEntity;
import com.nfs.front.entity.NotificationLogEntity;
import com.nfs.front.model.NotificationType;
import com.nfs.front.model.SenderType;
import com.nfs.front.repository.MemberRepository;
import com.nfs.front.repository.NotificationLogRepository;
import com.nfs.front.repository.NotificationRepository;
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
class CleanUpNotificationJobConfigTest {
    private JobLauncherTestUtils jobLauncherTestUtils;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private NotificationLogRepository notificationLogRepository;
    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private Job cleanUpNotificationJob;

    @BeforeEach
    void setUp() {
        this.jobLauncherTestUtils = new JobLauncherTestUtils();
        this.jobLauncherTestUtils.setJob(cleanUpNotificationJob);
        this.jobLauncherTestUtils.setJobLauncher(jobLauncher);
        this.jobLauncherTestUtils.setJobRepository(jobRepository);
    }

    @Test
    public void testCleanUpBeforeThreeMonthLogs() throws Exception {
        MemberEntity memberEntity = new MemberEntity(33L, "01013223344", "test", "yeoseon.yun@kakao.com", LocalDateTime.now());
        memberRepository.save(memberEntity);

        NotificationEntity notification = new NotificationEntity(11L, memberEntity.getMemberId(), NotificationType.IMMEDIATE, null, "test", "test", LocalDateTime.now());
        notificationRepository.save(notification);

        NotificationLogEntity smsNotificationLog = new NotificationLogEntity(1L, notification.getNotificationId(), LocalDateTime.now().minusMonths(2), SenderType.SMS);
        NotificationLogEntity kakaoNotificationLog = new NotificationLogEntity(2L, notification.getNotificationId(), LocalDateTime.now().minusMonths(5), SenderType.KAKAO_TALK);
        NotificationLogEntity emailNotificationLog = new NotificationLogEntity(3L, notification.getNotificationId(), LocalDateTime.now().minusMonths(4), SenderType.EMAIL);
        notificationLogRepository.saveAll(List.of(smsNotificationLog, kakaoNotificationLog, emailNotificationLog));

        JobParameters jobParameters = new JobParametersBuilder()
                .addString("now", LocalDateTime.now().toString())
                .toJobParameters();

        jobLauncherTestUtils.setJob(jobLauncherTestUtils.getJob());
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);
        assertEquals(jobExecution.getStatus(), BatchStatus.COMPLETED);

        List<NotificationLogEntity> remainLogEntities = notificationLogRepository.findAll();
        assertEquals(1, remainLogEntities.size());

    }

}