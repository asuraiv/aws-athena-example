package com.asuraiv.awsathena.example

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [DataSourceAutoConfiguration::class])
class AwsAthenaExampleApplication

fun main(args: Array<String>) {
	runApplication<AwsAthenaExampleApplication>(*args)
}
