package com.example.kata.batch;

import com.example.kata.batch.processor.NumberItemProcessor;
import com.example.kata.batch.reader.NumberItemReader;
import com.example.kata.batch.skip.NumberSkipPolicy;
import com.example.kata.batch.writer.NumberResultWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.PlatformTransactionManager;


@Configuration
@Profile("batch")
public class TransformationBatchJob {


    @Value("${batch.input-file}")
    private String inputFile;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private NumberItemReader numberItemReader;

    @Autowired
    private NumberItemProcessor numberItemProcessor;

    @Autowired
    private NumberResultWriter numberResultWriter;

    @Autowired
    private NumberSkipPolicy numberSkipPolicy;

    @Autowired
    private StepExecutionListener transformationListener;

    @Bean
    @Qualifier("transformationJob")
    public Job transformationJob() throws Exception {
        return new JobBuilder("transformationJob", jobRepository)
                .start(transformationStep())
                .build();
    }

    @Bean
    public Step transformationStep() throws Exception {
        return new StepBuilder("transformationStep", jobRepository)
                .<Integer, String>chunk(2, transactionManager)
                .reader(numberItemReader)
                .processor(numberItemProcessor)
                .writer(numberResultWriter)
                .faultTolerant()
                .skipPolicy(numberSkipPolicy)
                .listener(transformationListener)
                .build();
    }

}
