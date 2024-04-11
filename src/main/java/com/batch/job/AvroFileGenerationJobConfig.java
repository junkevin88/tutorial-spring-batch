package com.batch.job;

import com.batch.entity.DataUser;
import com.batch.service.AvroToCsvService;
import org.apache.avro.generic.GenericRecord;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class AvroFileGenerationJobConfig {

    @Autowired
    public AvroToCsvService avroToCsvService;


    @Bean
    public Job job(JobBuilderFactory jobs, Step userToAvroStep, Step avroToCsvStep) throws Exception {
        return jobs.get("avroFileGenerationJob")
                .flow(userToAvroStep)
                .next(avroToCsvStep)
                .end()
                .build();
    }

    @Bean
    public Step avroToCsvStep(StepBuilderFactory steps) throws Exception {
        return steps.get("avroToCsvStep")
                .tasklet((contribution, chunkContext) -> avroToCsvService.execute(contribution, chunkContext))  // Call execute method
                .build();
    }


    @Bean
    public Step userToAvroStep(StepBuilderFactory steps,
                               ItemReader<DataUser> userReader,
                               ItemWriter<GenericRecord> avroWriter) throws Exception {
        return steps.get("userToAvroStep")
                .<DataUser, GenericRecord>chunk(100000) // Adjust chunk size as needed
                .reader(userReader)
                .writer(avroWriter)
                .build();
    }

}
