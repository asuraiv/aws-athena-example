package com.asuraiv.awsathena.example.job

import com.amazonaws.services.s3.AmazonS3
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.stereotype.Component

@Component
class S3UploadTasklet(
    val s3Client: AmazonS3
) : Tasklet {

    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus? {

        val bucketName = "asuraiv-test"

        s3Client.putObject(bucketName, "2020-01-01", "Hello World!")

        return RepeatStatus.FINISHED
    }
}