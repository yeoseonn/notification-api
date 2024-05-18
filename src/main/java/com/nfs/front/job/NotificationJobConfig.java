package com.nfs.front.job;

import com.nfs.front.entity.NotificationItemEntity;
import com.nfs.front.repository.NotificationItemRepository;
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
import org.springframework.beans.factory.annotation.Value;
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
public class NotificationJobConfig {
    private final NotificationItemRepository notificationItemRepository;
    private final NotificationSendService notificationSendService;
    private static final int CHUNK_SIZE = 1000;

    @Bean
    public Job notifyTriggerJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobBuilder("notifyTriggerJob", jobRepository)
                .start(notifyTriggerStep(jobRepository, transactionManager))
                .build();
    }

    @Bean
    public Step notifyTriggerStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("notifyTriggerStep", jobRepository)
                .<NotificationItemEntity, NotificationItemEntity>chunk(CHUNK_SIZE, transactionManager)
                .reader(notifyItemReader(LocalDateTime.now()))
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    @StepScope
    public ItemReader<NotificationItemEntity> notifyItemReader(@Value("#{jobParameters[now]}") LocalDateTime now) {
        RepositoryItemReader<NotificationItemEntity> notificationItemEntityRepositoryItemReader = new RepositoryItemReader<>();
        notificationItemEntityRepositoryItemReader.setRepository(notificationItemRepository);
        notificationItemEntityRepositoryItemReader.setMethodName("findByNotifyAtBetween");
        notificationItemEntityRepositoryItemReader.setArguments(
                List.of(LocalDateTime.now().minusSeconds(30), LocalDateTime.now().plusSeconds(30)));
        notificationItemEntityRepositoryItemReader.setSort(Collections.singletonMap("notificationItemId", Sort.Direction.ASC));
        return notificationItemEntityRepositoryItemReader;
    }

    @Bean
    public ItemProcessor<NotificationItemEntity, NotificationItemEntity> processor() {
        return notificationItemEntity -> {
            notificationSendService.sendNotification(notificationItemEntity);
            return notificationItemEntity;
        };
    }

    @Bean
    public ItemWriter<NotificationItemEntity> writer() {
        RepositoryItemWriter<NotificationItemEntity> notificationItemEntityRepositoryItemWriter = new RepositoryItemWriter<>();
        notificationItemEntityRepositoryItemWriter.setRepository(notificationItemRepository);
        notificationItemEntityRepositoryItemWriter.setMethodName("delete");
        return notificationItemEntityRepositoryItemWriter;
    }
}
