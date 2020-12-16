package id.putraprima.mygoldtracker.data.db.converter

import androidx.room.TypeConverter
import java.math.BigDecimal
import java.util.*

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }

    @TypeConverter
    fun fromDoubleToBigDecimal(double: Double?): BigDecimal {
        return double?.let { BigDecimal(it) } ?: BigDecimal.ZERO
    }

    @TypeConverter
    fun fromBigDecimalToDouble(bigDecimal: BigDecimal?): Double {
        return bigDecimal?.toDouble() ?: 0.0
    }
}