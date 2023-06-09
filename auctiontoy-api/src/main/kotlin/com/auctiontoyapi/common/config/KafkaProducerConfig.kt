//package com.auctiontoyapi.common.config
//
//import org.apache.kafka.clients.producer.ProducerConfig
//import org.apache.kafka.common.serialization.IntegerSerializer
//import org.apache.kafka.common.serialization.StringSerializer
//import org.springframework.beans.factory.annotation.Value
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.kafka.core.DefaultKafkaProducerFactory
//import org.springframework.kafka.core.KafkaTemplate
//import java.io.Serializable
//
//@Configuration
//@ConditionalOnProperty(value = ["spring.kafka.type"], havingValue = "default")
//class KafkaProducerConfig {
//
//    @Value("\${spring.kafka.producer.bootstrap-servers}")
//    private lateinit var BOOTSTRAP_SERVER: String
//
//    @Bean("kafkaTemplate")
//    fun stringTemplate(): KafkaTemplate<String, String> {
//        val factory = DefaultKafkaProducerFactory<String, String>(producerStringConfigs())
//        return KafkaTemplate(factory)
//    }
//
//    @Bean("kafkaTemplate1")
//    fun intTemplate(): KafkaTemplate<String, Int> {
//        val factory = DefaultKafkaProducerFactory<String, Int>(productIntConfigs())
//        return KafkaTemplate(factory)
//    }
//
//    @Bean
//    fun anyTemplate(): KafkaTemplate<Any, Any> {
//        val factory = DefaultKafkaProducerFactory<Any, Any>(producerStringConfigs())
//
//        factory.isProducerPerThread = true
//
//        // Must Call closeThreadBoundProducer()
////        factory.closeThreadBoundProducer()
//
//
//        // update configs
////        val updateConfigs = mutableMapOf<String, Any>()
////        producerFactory.updateConfigs(updateConfigs)
//
//        // remove configs
////        val removeConfigKey = "ConfigKey"
////        producerFactory.removeConfig(removeConfigKey)
//
//        return KafkaTemplate(factory)
//    }
//
//    fun producerStringConfigs(): Map<String, Serializable> =
//        mapOf(
//            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to BOOTSTRAP_SERVER,
//            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
//            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java
//        )
//
//    fun productIntConfigs(): Map<String, Serializable> =
//        mapOf(
//            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to BOOTSTRAP_SERVER,
//            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
//            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to IntegerSerializer::class.java
//        )
//
//}