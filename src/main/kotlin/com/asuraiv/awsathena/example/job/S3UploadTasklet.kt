package com.asuraiv.awsathena.example.job

import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.stereotype.Component
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.PutObjectRequest


@Component
class S3UploadTasklet(
    val s3Client: S3Client
) : Tasklet {

    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus? {

        s3Client.putObject(
            PutObjectRequest.builder()
                .bucket("asuraiv-test")
                .key("date=2020-01-01/hello_message.txt")
                .build(),
            RequestBody.fromString("Hello World!")
        )

        return RepeatStatus.FINISHED
    }
}