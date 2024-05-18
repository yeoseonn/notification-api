package com.nfs.front.job;

import com.nfs.front.entity.NotificationEntity;
import com.nfs.front.entity.NotificationFailLogEntity;
import com.nfs.front.entity.NotificationLogEntity;
import com.nfs.front.repository.NotificationFailLogRepository;
import com.nfs.front.repository.NotificationLogRepository;
import com.nfs.front.service.NotificationSendService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class CleanUpNotificationJobConfig {
    private final NotificationLogRepository notificationLogRepository;
    private static final int CHUNK_SIZE = 1000;

    @Bean
    public Job cleanUpNotificationJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobBuilder("cleanUpNotificationJob", jobRepository)
                .start(cleanUpNotificationStep(jobRepository, transactionManager))
                .build();
    }

    @Bean
    public Step cleanUpNotificationStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("cleanUpNotificationStep", jobRepository)
                .<NotificationLogEntity, NotificationLogEntity>chunk(CHUNK_SIZE, transactionManager)
                .reader(notificationLogItemReader())
                .writer(notificationLogEntityItemWriter())
                .build();
    }

    @Bean
    @StepScope
    public ItemReader<NotificationLogEntity> notificationLogItemReader() {
        RepositoryItemReader<NotificationLogEntity> notificationLogEntityRepositoryItemReader = new RepositoryItemReader<>();
        notificationLogEntityRepositoryItemReader.setRepository(notificationLogRepository);
        notificationLogEntityRepositoryItemReader.setMethodName("findBySentAtBefore");
        notificationLogEntityRepositoryItemReader.setArguments(
                List.of(LocalDateTime.now().minusMonths(3)));
        notificationLogEntityRepositoryItemReader.setSort(Collections.singletonMap("notificationLogId", Sort.Direction.ASC));
        return notificationLogEntityRepositoryItemReader;
    }

    @Bean
    public ItemWriter<NotificationLogEntity> notificationLogEntityItemWriter() {
        RepositoryItemWriter<NotificationLogEntity> notificationLogEntityRepositoryItemWriter = new RepositoryItemWriter<>();
        notificationLogEntityRepositoryItemWriter.setRepository(notificationLogRepository);
        notificationLogEntityRepositoryItemWriter.setMethodName("delete");
        return notificationLogEntityRepositoryItemWriter;
    }
}
