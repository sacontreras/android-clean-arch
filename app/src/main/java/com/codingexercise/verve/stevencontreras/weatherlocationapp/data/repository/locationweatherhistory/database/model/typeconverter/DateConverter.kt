package com.codingexercise.verve.stevencontreras.weatherlocationapp.data.repository.locationweatherhistory.database.model.typeconverter

import android.arch.persistence.room.TypeConverter
import java.util.*

class DateConverter {
    companion object {
        @TypeConverter
        fun toDate(timestamp: Long?): Date? {
            return if (timestamp == null) null else Date(timestamp)
        }

        @TypeConverter
        fun toTimestamp(date: Date?): Long? {
            return (if (date == null) null else date!!.getTime())?.toLong()
        }
    }
}