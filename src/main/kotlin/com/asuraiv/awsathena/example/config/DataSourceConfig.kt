package com.asuraiv.awsathena.example.config

import com.zaxxer.hikari.HikariDataSource
import org.apache.ibatis.session.SqlSessionFactory
import org.mybatis.spring.SqlSessionFactoryBean
import org.mybatis.spring.annotation.MapperScan
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.core.io.support.PathMatchingResourcePatternResolver
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.sql.DataSource

@Configuration
@EnableTransactionManagement
@MapperScan(
    basePackages = ["com.asuraiv.awsathena.example"],
    sqlSessionFactoryRef = "sessionFactory"
)
class DataSourceConfig {


    @Primary
    @Bean
    @ConfigurationProperties("spring.datasource.athena")
    fun dataSource(): DataSource {

        return DataSourceBuilder
            .create()
            .type(HikariDataSource::class.java)
            .build()
    }

    @Primary
    @Bean
    fun sessionFactory(): SqlSessionFactory {

        val sqlSessionFactoryBean = SqlSessionFactoryBean()

        sqlSessionFactoryBean.setDataSource(this.dataSource())
        sqlSessionFactoryBean.setTypeAliasesPackage("com.asuraiv.awsathena.example")
        sqlSessionFactoryBean.setMapperLocations(PathMatchingResourcePatternResolver().getResources("classpath:/mapper/*.xml"))
        sqlSessionFactoryBean.vfs = SpringBootVFS::class.java

        return sqlSessionFactoryBean.`object`!!
    }
}