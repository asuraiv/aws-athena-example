package com.asuraiv.awsathena.example.job.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.*
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.athena.AthenaClient

@Configuration
class AthenaConfig {


    @Bean
    fun athenaClient(): AthenaClient {
        return AthenaClient.builder()
            .region(Region.AP_NORTHEAST_2)
            .credentialsProvider(SystemPropertyCredentialsProvider.create())
            .build()
    }
}