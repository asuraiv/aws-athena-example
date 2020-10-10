package com.asuraiv.awsathena.example.job.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.SystemPropertyCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.athena.AthenaClient
import software.amazon.awssdk.services.s3.S3Client

@Configuration
class AWSConfig {

    @Bean
    fun athenaClient(): AthenaClient {
        return AthenaClient.builder()
            .region(Region.AP_NORTHEAST_2)
            .credentialsProvider(SystemPropertyCredentialsProvider.create())
            .build()
    }

    @Bean
    fun s3Client(): S3Client? {
        return S3Client.builder()
            .region(Region.AP_NORTHEAST_2)
            .credentialsProvider(SystemPropertyCredentialsProvider.create())
            .build()
    }
}