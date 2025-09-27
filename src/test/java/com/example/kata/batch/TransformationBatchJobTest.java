package com.example.kata.batch;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBatchTest
@SpringBootTest
@ActiveProfiles("batch")

public class TransformationBatchJobTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private Job transformationJob;

    @Test
    public void testJobLaunch() throws Exception {


        Logger logger = (Logger) LoggerFactory.getLogger("com.example.kata.batch.listener.TransformationListener");

        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();
        logger.addAppender(listAppender);

        JobExecution jobExecution = jobLauncherTestUtils.launchJob(
                new JobParametersBuilder()
                        .addLong("time", System.currentTimeMillis())
                        .toJobParameters());

        assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);

        List<String> logMessages = listAppender.list.stream()
                .map(ILoggingEvent::getFormattedMessage)
                .toList();

        // assert listener log
        assertThat(logMessages).anyMatch(msg -> msg.contains("Nombres NULL"));
        assertThat(logMessages).anyMatch(msg -> msg.contains("Nombres non valides"));
        assertThat(logMessages).anyMatch(msg -> msg.contains("Nombres hors plage"));
        assertThat(logMessages).anyMatch(msg -> msg.contains("Nombres traités"));

    }

}
