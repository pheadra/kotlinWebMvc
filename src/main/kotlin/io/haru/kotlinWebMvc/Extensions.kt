package io.haru.kotlinWebMvc

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.YearMonth
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

/// 머든 공용으로 쓸 수 있는 Extension 함수를 모아 놓고 가져다 쓴다

fun Long.toLocalDateTime() : LocalDateTime {
    return LocalDateTime.ofInstant(
        Instant.ofEpochMilli(this),
        TimeZone.getDefault().toZoneId()
    )
}

fun Long.toYearMonth() : YearMonth {
    val localDateTime = LocalDateTime.ofInstant(
        Instant.ofEpochMilli(this),
        TimeZone.getDefault().toZoneId()
    )

    return YearMonth.of(localDateTime.year, localDateTime.monthValue)
}

fun LocalDate.toYm(): String {
    return "%04d%02d".format(year, month.value)
}

fun YearMonth.toYm(): String {
    return this.format(DateTimeFormatter.ofPattern("yyyyMM"))
}

fun Date.toLocalDateTime() : LocalDateTime {
    return LocalDateTime.ofInstant(this.toInstant(), ZoneId.systemDefault())
}

fun Date.toYearMonth() : YearMonth {
    return this.time.toYearMonth()
}