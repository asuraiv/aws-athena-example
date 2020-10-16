package com.asuraiv.awsathena.example

import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.runApplication
import javax.sql.DataSource

@EnableBatchProcessing
@SpringBootApplication(exclude = [DataSourceAutoConfiguration::class])
class AwsAthenaExampleApplication : DefaultBatchConfigurer() {

	override fun setDataSource(dataSource: DataSource) {
	}
}

fun main(args: Array<String>) {
	runApplication<AwsAthenaExampleApplication>(*args)
}
