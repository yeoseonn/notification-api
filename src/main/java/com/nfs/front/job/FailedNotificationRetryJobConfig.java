package com.nfs.front.job;

import com.nfs.front.entity.NotificationFailLogEntity;
import com.nfs.front.repository.NotificationFailLogRepository;
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

import java.util.Collections;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class FailedNotificationRetryJobConfig {
    private final NotificationFailLogRepository notificationFailLogRepository;
    private final NotificationSendService notificationSendService;
    private static final int CHUNK_SIZE = 1000;

    @Bean
    public Job notifyRetryJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobBuilder("notifyRetryJob", jobRepository).start(notifyRetryStep(jobRepository, transactionManager)).build();
    }

    @Bean
    public Step notifyRetryStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("notifyRetryStep", jobRepository).<NotificationFailLogEntity, NotificationFailLogEntity>chunk(CHUNK_SIZE, transactionManager).reader(retryItemReader()).processor(
                retryProcessor()).writer(retryWriter()).build();
    }

    @Bean
    @StepScope
    public ItemReader<NotificationFailLogEntity> retryItemReader() {
        RepositoryItemReader<NotificationFailLogEntity> notificationFailLogEntityRepositoryItemReader = new RepositoryItemReader<>();
        notificationFailLogEntityRepositoryItemReader.setRepository(notificationFailLogRepository);
        notificationFailLogEntityRepositoryItemReader.setMethodName("findAll");
        notificationFailLogEntityRepositoryItemReader.setSort(Collections.singletonMap("notificationFailLogId", Sort.Direction.ASC));
        return notificationFailLogEntityRepositoryItemReader;
    }

    @Bean
    public ItemProcessor<NotificationFailLogEntity, NotificationFailLogEntity> retryProcessor() {
        return notificationFailLogEntity -> {
            boolean success = notificationSendService.retrySendNotification(notificationFailLogEntity);
            if (success) {
                return notificationFailLogEntity;
            } else {
                return null;
            }
        };
    }

    @Bean
    public ItemWriter<NotificationFailLogEntity> retryWriter() {
        RepositoryItemWriter<NotificationFailLogEntity> notificationFailLogEntityRepositoryItemWriter = new RepositoryItemWriter<>();
        notificationFailLogEntityRepositoryItemWriter.setRepository(notificationFailLogRepository);
        notificationFailLogEntityRepositoryItemWriter.setMethodName("delete");
        return notificationFailLogEntityRepositoryItemWriter;
    }
}
