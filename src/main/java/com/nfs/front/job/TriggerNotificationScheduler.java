package com.nfs.front.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

@Slf4j
@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class TriggerNotificationScheduler {
    private final Job notifyTriggerJob;
    private final Job notifyRetryJob;
    private final Job cleanUpNotificationJob;
    private final JobLauncher jobLauncher;

    @Scheduled(fixedRate = 60000)
    public void startNotificationJob() {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("version", LocalDateTime.now().toString()).toJobParameters();
            jobLauncher.run(notifyTriggerJob, jobParameters);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Scheduled(fixedRate = 30000)
    public void startFailedNotificationRetryJob() {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("version", LocalDateTime.now().toString()).toJobParameters();
            jobLauncher.run(notifyRetryJob, jobParameters);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
    @Scheduled(cron = "0 0 1 * * *")
    public void startCleanUpNotificationJob() {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("version", LocalDateTime.now().toString()).toJobParameters();
            jobLauncher.run(cleanUpNotificationJob, jobParameters);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }



}
