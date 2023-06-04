package com.auctiontoyapi

import com.auctionpersistence.jpa.configuration.EnableDataSourceConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication(
    exclude = [
        DataSourceAutoConfiguration::class
    ]
)
@EnableDataSourceConfiguration
@ConfigurationPropertiesScan
@EnableScheduling
class AuctionToyApiApplication

fun main(args: Array<String>) {
    System.setProperty("spring.config.name", "application-api, application-persistence")
    runApplication<AuctionToyApiApplication>(*args)
}