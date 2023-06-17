package com.auctionpersistence.redis.lock

import com.auctionpersistence.redis.service.RedisService
import mu.KLogging
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.core.LocalVariableTableParameterNameDiscoverer
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import java.lang.Thread.sleep

//메서드나 클래스 기준으로 사용하는 annotation, 런타임떄 동작
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class RedisLock(
    val key: String,
    val name: String = "redisLock"
)

//해당 어노테이션의 우선순위
@Aspect
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
@Component
class RedisLockAspect(
    private val redisService: RedisService
) {

    @Pointcut("@annotation(RedisLock)")
    fun lockAnnotation() {

    }

    @Around("lockAnnotation() && @annotation(redisLock)")
    fun around(joinPoint: ProceedingJoinPoint, redisLock: RedisLock): Any? {

        val lockName = redisLock.name
        val parameters = getParameterMapByName(joinPoint)
        val key = parameters[redisLock.key]?.let {it as String} ?: throw Exception("${redisLock.key} 와 일치하는 Parameter 없습니다")

        while(redisService.lock(key, lockName)?.not() != false){
            logger.info("$key is locked now")
            sleep(1_000)
        }

        return try {
            joinPoint.proceed()
        } finally {
            redisService.unlock(key)
        }

    }

    /**
     * 파라미터와 값을 매핑해주는 메서드
     * @param : annotation 이 선언된 메서드의 진입점에서 해당 메서드에 대한 정보를 담고 있는 객체
     * @return : parameter 와 실제 값을 매핑한 정보
     * */
    private fun getParameterMapByName(joinPoint: JoinPoint): Map<String, Any> {
        val parameterMapByName: MutableMap<String, Any> = mutableMapOf()
        // 파라미터의 값
        val args = joinPoint.args
        val signature = joinPoint.signature as MethodSignature
        val method = joinPoint.target.javaClass.getDeclaredMethod(
            signature.method.name,
            *signature.method.parameterTypes
        )
        // 파라미터의 이름
        val parameterNames = LocalVariableTableParameterNameDiscoverer().getParameterNames(method) ?: return mutableMapOf()
        for (index in parameterNames.indices) {
            parameterMapByName[parameterNames[index]] = args[index]
        }
        return parameterMapByName
    }

    companion object : KLogging()
}
