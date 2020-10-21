package com.asuraiv.awsathena.example.job

import com.asuraiv.awsathena.example.job.helper.ParquetGenerator
import com.asuraiv.proto.messages.UserProto
import com.google.protobuf.Timestamp
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.stereotype.Component
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


@Component
class S3UploadTasklet(
    val s3Client: S3Client
) : Tasklet {

    val log: Logger = LoggerFactory.getLogger(S3UploadTasklet::class.java)

    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus? {

        val users = listOf<UserProto.User>(
            makeUser(userId = 1L, userName = "mathew", age = 15),
            makeUser(userId = 2L, userName = "mark", age = 20, email = "mark@asuraiv.com"),
            makeUser(userId = 3L, userName = "luke", age = 13, email = "luke@asuraiv.com", phone = "010-8282-1111"),
            makeUser(userId = 4L, userName = "john", age = 22, email = "john@asuraiv.com", phone = "010-9999-2222"),
            makeUser(userId = 5L, userName = "tom", age = 20),
            makeUser(userId = 6L, userName = "jake", age = 51),
            makeUser(userId = 7L, userName = "rover", age = 36, email = "asuraiv7@asuraiv.com"),
            makeUser(userId = 8L, userName = "raul", age = 43),
            makeUser(userId = 9L, userName = "rulu", age = 41),
            makeUser(userId = 10L, userName = "rouka", age = 41)
        )

        val parquetGenerator = ParquetGenerator()
        val path = parquetGenerator.generate(users = users)



        val yyyyMMdd = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        val key = path.name

        val finalBucketName = "asuraiv-test/date=${yyyyMMdd}"
        val file = File(path.toUri().path)

        try {
            s3Client.putObject(
                PutObjectRequest.builder()
                    .bucket("asuraiv-test")
                    .key("date=${yyyyMMdd}/${path.name}")
                    .build(),
                RequestBody.fromFile(file)
            )
            log.info("S3 Upload completed. bucket: $finalBucketName, key: $key")
        } catch(e: Exception) {
            log.error("Error occur during upload to s3.", e)
        } finally {
            file.delete()
            File("tmp/.${path.name}.crc").delete()
        }

        return RepeatStatus.FINISHED
    }

    private fun makeUser(userId: Long, userName: String, age: Int, email: String = "", phone: String = "") =
        UserProto.User.newBuilder()
            .setUserId(userId)
            .setUserName(userName)
            .setAge(age)
            .setEmail(email)
            .setPhone(phone)
            .setCreatedAt(Timestamp.newBuilder()
                .setSeconds(Date().seconds.toLong())
            )
            .build()
}