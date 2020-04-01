package io.santisme.ahoy.sources.additions

import java.text.SimpleDateFormat
import java.util.*

object TimeOffsetRepository {

    private const val MINUTES_MILLIS = 1000L * 60
    private const val HOUR_MILLIS = MINUTES_MILLIS * 60
    private const val DAY_MILLIS = HOUR_MILLIS * 24
    private const val MONTH_MILLIS = DAY_MILLIS * 30
    private const val YEAR_MILLIS = MONTH_MILLIS * 12

    data class TimeOffset(
        val amount: Int,
        val unit: Int
    )

    fun getTimeOffset(dateToCompare: String): TimeOffset {
        val current = Date().time

        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val createdAt = parser.parse(dateToCompare)

        createdAt?.let {
            val diff = current - createdAt.time

            val years = diff / YEAR_MILLIS
            if (years > 0) return TimeOffset(
                years.toInt(),
                Calendar.YEAR
            )

            val months = diff / MONTH_MILLIS
            if (months > 0) return TimeOffset(
                months.toInt(),
                Calendar.MONTH
            )

            val days = diff / DAY_MILLIS
            if (days > 0) return TimeOffset(
                days.toInt(),
                Calendar.DAY_OF_MONTH
            )

            val hours = diff / HOUR_MILLIS
            if (hours > 0) return TimeOffset(
                hours.toInt(),
                Calendar.HOUR
            )

            val minutes = diff / MINUTES_MILLIS
            if (minutes > 0) return TimeOffset(
                minutes.toInt(),
                Calendar.MINUTE
            )
        }

        return TimeOffset(0, Calendar.MINUTE)
    }
}