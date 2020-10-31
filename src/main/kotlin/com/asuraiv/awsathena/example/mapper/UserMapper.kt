package com.asuraiv.awsathena.example.mapper

import com.asuraiv.awsathena.example.vo.User
import org.springframework.stereotype.Repository

@Repository
interface UserMapper {
    fun findAll(): List<User>
}