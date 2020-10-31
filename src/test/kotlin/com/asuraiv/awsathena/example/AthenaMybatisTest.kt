package com.asuraiv.awsathena.example

import com.asuraiv.awsathena.example.mapper.UserMapper
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner

@ActiveProfiles("local")
@RunWith(SpringRunner::class)
@SpringBootTest
class AthenaMybatisTest {

    @Autowired
    lateinit var userMapper: UserMapper

    @Test
    fun testAthenaMybatis() {

        val list = userMapper.findAll()

        list.forEach(::println)
    }
}