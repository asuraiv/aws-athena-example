package com.asuraiv.awsathena.example.job.vo

import java.util.*

data class BusLocation(

    val busLocationId: Long? = null,
    val vehicleId: String = "",
    val localLineId: String = "",
    val nodeId: String = "",
    val nodeSeq: Int = -1,
    val busStop: Int = -1,
    val localStationId: String = "",
    val arsId: String = "",
    val dataType: String = "",
    val cityCode: Int = -1,
    val providedAt: Date = Date(),
    val createdAt: Date = Date()
)
