package com.auctiontoyapi

import com.auctionpersistence.jpa.configuration.EnableDataSourceConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication(
    exclude = [
        DataSourceAutoConfiguration::class
    ]
)
@EnableDataSourceConfiguration
@ConfigurationPropertiesScan
class AuctionToyApiApplication

fun main(args: Array<String>) {
    System.setProperty("spring.config.name", "application-api, application-persistence")
    runApplication<AuctionToyApiApplication>(*args)
}