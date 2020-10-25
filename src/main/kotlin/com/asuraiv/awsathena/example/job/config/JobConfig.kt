package com.asuraiv.awsathena.example.job.config

import com.asuraiv.awsathena.example.job.AthenaMybatisSampleTasklet
import org.springframework.batch.core.Job
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.step.tasklet.TaskletStep
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JobConfig(
    val jobBuilderFactory: JobBuilderFactory,
    val stepBuilderFactory: StepBuilderFactory,
    val athenaMybatisSampleTasklet: AthenaMybatisSampleTasklet
) {

    @Bean
    fun athenaSampleJob(): Job {
        return jobBuilderFactory["athenaSampleJob"]
            .start(athenaSampleStep())
            .build()
    }

    @Bean
    fun athenaSampleStep(): TaskletStep {
        return stepBuilderFactory["createBusScheduleStep"]
            .tasklet(athenaMybatisSampleTasklet)
            .build()
    }
}