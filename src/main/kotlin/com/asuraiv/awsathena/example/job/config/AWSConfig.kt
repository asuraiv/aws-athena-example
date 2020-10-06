package com.asuraiv.awsathena.example.job.config

import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.*
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.athena.AthenaClient

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
    fun s3Client(): AmazonS3? {
        return AmazonS3ClientBuilder.standard()
            .withRegion(Regions.AP_NORTHEAST_2)
            .build()
    }
}