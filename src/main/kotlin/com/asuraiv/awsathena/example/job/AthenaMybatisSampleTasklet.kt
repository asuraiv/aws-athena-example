package com.asuraiv.awsathena.example.job

import com.asuraiv.awsathena.example.job.mapper.BusLocationMapper
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.stereotype.Component

@Component
class AthenaMybatisSampleTasklet(
    val busLocationMapper: BusLocationMapper
) : Tasklet {


    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus? {

        val list = busLocationMapper.findAll()

        list.forEach(::println)

        return RepeatStatus.FINISHED
    }
}