package com.asuraiv.awsathena.example.helper

import com.asuraiv.proto.messages.UserProto
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.Path
import org.apache.parquet.proto.ProtoParquetWriter
import org.apache.parquet.proto.ProtoWriteSupport
import java.util.*

class ParquetGenerator {

    fun generate(users: List<UserProto.User>): Path {

        val path = Path("tmp/users_${UUID.randomUUID()}.parquet")

        val conf = Configuration()
        ProtoWriteSupport.setWriteSpecsCompliant(conf, true)

        val parquetWriter = ProtoParquetWriter<UserProto.User>(path, UserProto.User::class.java)

        for (user in users) {
            parquetWriter.write(user)
        }

        parquetWriter.close()

        return path
    }
}