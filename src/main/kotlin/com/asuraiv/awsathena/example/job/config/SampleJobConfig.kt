package com.asuraiv.awsathena.example.job.config

import com.asuraiv.awsathena.example.job.SampleTasklet
import org.springframework.batch.core.Job
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.step.tasklet.TaskletStep
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SampleJobConfig(
    val jobBuilderFactory: JobBuilderFactory,
    val stepBuilderFactory: StepBuilderFactory,
    val sampleTasklet: SampleTasklet
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
            .tasklet(sampleTasklet)
            .build()
    }
}