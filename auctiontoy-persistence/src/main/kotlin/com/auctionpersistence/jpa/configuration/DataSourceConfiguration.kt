package com.auctionpersistence.jpa.configuration

import com.auctionpersistence.jpa.Jpa
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.hibernate.cfg.AvailableSettings
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.persistence.EntityManagerFactory
import javax.sql.DataSource

@Configuration
@ConditionalOnClass(EnableDataSourceConfiguration::class)
@EnableJpaRepositories(
    entityManagerFactoryRef = "auctionEntityManagerFactory",
    transactionManagerRef = "auctionTransactionManager",
    basePackageClasses = [Jpa::class]
)
@EnableTransactionManagement
@EnableJpaAuditing
class DataSourceConfiguration {
    @Bean
    @Primary
    @ConfigurationProperties(prefix = "auction.datasource")
    fun auctionDataSourceProperties(): HikariConfig {
        return HikariConfig()
    }

    @Bean
    fun auctionDataSource(): DataSource {
        val data = HikariDataSource(auctionDataSourceProperties())
        return LazyConnectionDataSourceProxy(data)
    }

    @Bean
    fun auctionEntityManagerFactory(builder: EntityManagerFactoryBuilder): LocalContainerEntityManagerFactoryBean {
        val properties = HashMap<String, String>()
        properties[AvailableSettings.USE_SECOND_LEVEL_CACHE] = false.toString()
        properties[AvailableSettings.USE_QUERY_CACHE] = false.toString()

        return builder.dataSource(auctionDataSource()).packages(Jpa::class.java).properties(properties).persistenceUnit("auction").build()
    }

    @Primary
    @Bean("auctionTransactionManager")
    fun auctionTransactionManager(@Qualifier("auctionEntityManagerFactory") factory: EntityManagerFactory) = JpaTransactionManager(factory)
}