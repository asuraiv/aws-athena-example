package com.asuraiv.awsathena.example.job.vo

import java.util.*

data class User(
    val userId: Long = -1L,
    val userName: String = "",
    val age: Int = -1,
    val email: String = "",
    val phone: String = "",
    val createdAt: Date = Date(0L)
)