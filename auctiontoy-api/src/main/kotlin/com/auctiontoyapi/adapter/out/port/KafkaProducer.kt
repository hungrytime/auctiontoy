package com.auctiontoyapi.adapter.out.port

import com.auctiontoyapi.adapter.out.vo.BidItemVO
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import mu.KLogging
import org.springframework.kafka.core.KafkaProducerException
import org.springframework.kafka.core.KafkaSendCallback
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.stereotype.Component
import org.springframework.util.concurrent.ListenableFutureCallback

@Component
class KafkaProducer(
    val kafkaTemplate: KafkaTemplate<String, String>
) {
    fun listenableFutureCallback(message: String) =
        object: KafkaSendCallback<String, String> {
            override fun onSuccess(result: SendResult<String, String>?) {
                logger.info(
                    "Send Message = [ $message ] with offset=[ ${result!!.recordMetadata.offset()} ]"
                )
            }

            override fun onFailure(ex: KafkaProducerException) {
                logger.error(
                    "Message 전달 오류 [ $message ] due to: ${ex.getFailedProducerRecord<String, String>()}"
                )
            }
        }

    fun sendMessage(message: BidItemVO) {
        val jasonData: String = jacksonObjectMapper().writeValueAsString(message)
        val listenableFuture = kafkaTemplate.send(TOPIC, jasonData)
        listenableFuture.addCallback(listenableFutureCallback(jasonData))
    }

    companion object : KLogging() {
        const val TOPIC = "trybid"
    }
}