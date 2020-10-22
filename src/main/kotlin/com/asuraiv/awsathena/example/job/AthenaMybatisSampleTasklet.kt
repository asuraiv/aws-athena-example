package com.asuraiv.awsathena.example.job

import com.asuraiv.awsathena.example.job.mapper.UserMapper
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.stereotype.Component

@Component
class AthenaMybatisSampleTasklet(
    val userMapper: UserMapper
) : Tasklet {


    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus? {

        val list = userMapper.findAll()

        list.forEach(::println)

        return RepeatStatus.FINISHED
    }
}