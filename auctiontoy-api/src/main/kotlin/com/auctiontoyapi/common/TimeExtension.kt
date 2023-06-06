package com.auctiontoyapi.common

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun String.toLocalDateTime(formatter: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME) = LocalDateTime.parse(this, formatter)

class TimeExtension {
}