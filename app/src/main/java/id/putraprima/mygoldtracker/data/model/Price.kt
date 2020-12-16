package id.putraprima.mygoldtracker.data.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.math.BigDecimal
import java.util.*

@Entity(tableName = "price")
data class Price @Ignore constructor(
        @Ignore val weight: Double = 1.0,
        @PrimaryKey val date: Date,
        val buy: BigDecimal,
        val sell: BigDecimal

) {
    constructor(date: Date, buy: BigDecimal, sell: BigDecimal) : this(1.0, date, buy, sell)
}
