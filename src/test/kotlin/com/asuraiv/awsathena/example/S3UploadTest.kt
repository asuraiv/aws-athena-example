package com.asuraiv.awsathena.example

import com.asuraiv.awsathena.example.helper.ParquetGenerator
import com.asuraiv.proto.messages.UserProto
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.test.assertNotNull

@ActiveProfiles("local")
@RunWith(SpringRunner::class)
@SpringBootTest
class S3UploadTest {

	@Autowired
	lateinit var s3Client: S3Client

	@Test
	fun testUploadToS3() {

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

		val bucket = "asuraiv-test"
		val subBucket = "users/date=${yyyyMMdd}"
		val file = File(path.toUri().path)

		try {
			val response = s3Client.putObject(
				PutObjectRequest.builder()
					.bucket(bucket)
					.key("$subBucket/$key")
					.build(),
				RequestBody.fromFile(file)
			)

			assertNotNull(response)
			println("S3 Upload completed. bucket: $bucket/$subBucket/, key: $key")
		} catch(e: Exception) {
			throw e
		} finally {
			file.delete()
			File("tmp/.$key.crc").delete()
		}
	}

	private fun makeUser(userId: Long, userName: String, age: Int, email: String = "", phone: String = "") =
		UserProto.User.newBuilder()
			.setUserId(userId)
			.setUserName(userName)
			.setAge(age)
			.setEmail(email)
			.setPhone(phone)
			.setCreatedAt(Date().time)
			.build()

}
