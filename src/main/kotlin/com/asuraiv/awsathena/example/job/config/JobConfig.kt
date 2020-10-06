package com.asuraiv.awsathena.example.job.config

import com.asuraiv.awsathena.example.job.AthenaSampleTasklet
import com.asuraiv.awsathena.example.job.S3UploadTasklet
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
    val athenaSampleTasklet: AthenaSampleTasklet,
    val s3UploadTasklet: S3UploadTasklet
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
            .tasklet(athenaSampleTasklet)
            .build()
    }

    @Bean
    fun s3UploadJob(): Job {
        return jobBuilderFactory["s3UploadJob"]
            .start(s3UploadStep())
            .build()
    }

    @Bean
    fun s3UploadStep(): TaskletStep {
        return stepBuilderFactory["s3UploadStep"]
            .tasklet(s3UploadTasklet)
            .build()
    }
}