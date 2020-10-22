package com.asuraiv.awsathena.example.job.mapper

import com.asuraiv.awsathena.example.job.vo.User
import org.springframework.stereotype.Repository

@Repository
interface UserMapper {
    fun findAll(): List<User>
}