package com.asuraiv.awsathena.example

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import software.amazon.awssdk.services.athena.AthenaClient
import software.amazon.awssdk.services.athena.model.*
import software.amazon.awssdk.services.athena.paginators.GetQueryResultsIterable

@ActiveProfiles("local")
@RunWith(SpringRunner::class)
@SpringBootTest
class AthenaTest {

    @Autowired
    lateinit var athenaClient: AthenaClient

    @Test
    fun testBasicAthena() {

        val queryExecutionId = submitSampleQuery()

        waitForQueryToComplete(queryExecutionId)

        printResultRows(queryExecutionId)
    }

    /*
        Query 실행
     */
    private fun submitSampleQuery(): String {

        val queryExecutionContext = QueryExecutionContext.builder()
            .database("asuraiv_sample")
            .build()

        val resultConfiguration = ResultConfiguration.builder()
            .outputLocation("s3://asuraiv-test/query-results")
            .build()

        val startQueryExecutionRequest = StartQueryExecutionRequest.builder()
            .queryString("SELECT * FROM user LIMIT 10;")
            .queryExecutionContext(queryExecutionContext)
            .resultConfiguration(resultConfiguration)
            .build()


        return athenaClient.startQueryExecution(startQueryExecutionRequest).queryExecutionId()
    }

    /*
        Query 완료 대기
     */
    private fun waitForQueryToComplete(queryExecutionId: String) {

        val getQueryExecutionRequest = GetQueryExecutionRequest.builder()
            .queryExecutionId(queryExecutionId)
            .build()

        var getQueryExecutionResponse: GetQueryExecutionResponse

        var isRunning = true

        while (isRunning) {

            getQueryExecutionResponse = athenaClient.getQueryExecution(getQueryExecutionRequest)

            val state = getQueryExecutionResponse.queryExecution().status().state().toString()

            when (state) {

                QueryExecutionState.FAILED.toString() -> throw RuntimeException("Query Failed to run with Error Message: ${
                    getQueryExecutionResponse
                        .queryExecution().status().stateChangeReason()
                }")

                QueryExecutionState.CANCELLED.toString() -> throw RuntimeException("Query was canceled.")

                QueryExecutionState.SUCCEEDED.toString() -> isRunning = false

                else -> Thread.sleep(2000L)
            }

            println("Current state is $state")
        }
    }

    /*
        결과 행 출력
     */
    private fun printResultRows(queryExecutionId: String) {

        val getQueryResultsRequest = GetQueryResultsRequest.builder()
            .queryExecutionId(queryExecutionId)
            .build()

        val results: GetQueryResultsIterable = athenaClient.getQueryResultsPaginator(getQueryResultsRequest)

        for(result in results) {
            for (row in result.resultSet().rows()) {
                val datumList: List<Datum> = row.data()
                for (datum in datumList) {
                    print("${datum.varCharValue()}\t")
                }
                println()
            }
        }
    }

}