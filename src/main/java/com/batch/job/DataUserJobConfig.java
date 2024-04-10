package com.batch.job;

import com.batch.entity.DataUser;
import com.batch.service.DataUserService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class DataUserJobConfig {
    @Autowired
    public DataUserService dataUserService;

    @Bean
    public Step stepAddData() {
        return this.dataUserService.stepBuilderFactory.get("Step Add Data")
                .<DataUser, DataUser>chunk(1000)
                .reader(this.dataUserService.personItemReader())
                .writer(this.dataUserService.personItemWriter())
                .build();
    }

    @Bean
    public Job job1() {
        return this.dataUserService.jobBuilderFactory.get("job")
                .start(stepAddData())
                .build();
    }

}
