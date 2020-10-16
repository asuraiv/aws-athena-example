package com.asuraiv.awsathena.example.job.mapper

import com.asuraiv.awsathena.example.job.vo.BusLocation
import org.springframework.stereotype.Repository

@Repository
interface BusLocationMapper {
    fun findAll(): List<BusLocation>
}