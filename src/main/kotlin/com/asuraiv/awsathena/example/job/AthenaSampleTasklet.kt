package com.asuraiv.awsathena.example.job

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.stereotype.Component

@Component
class AthenaSampleTasklet : Tasklet {

    val log: Logger = LoggerFactory.getLogger(AthenaSampleTasklet::class.java)

    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus? {

        log.info("Execute athena sample tasklet")

        return RepeatStatus.FINISHED
    }
}